package com.generation.javabnb.controller.util;

import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.generation.javabnb.exception.InvalidEntityException;



@RestControllerAdvice
public class GestoreEccezioni 
{
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e)
	{
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidEntityException.class)
	public ResponseEntity<String> handleInvalidDataException(InvalidEntityException e)
	{
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e)
	{
		return new ResponseEntity<String>("Occhio al parametro in ingresso.", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidDataAccessResourceUsageException.class)
	public ResponseEntity<String> handleInvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException e)
	{
		return new ResponseEntity<String>("DB fuori uso.", HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<String>handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e)
	{
		return new ResponseEntity<String>("Metodo non disponibile con questo URI", HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<String>handleHttpMessageNotReadableException(HttpMessageNotReadableException e)
	{
		return new ResponseEntity<String>("Il JSON non e in formato corretto!",HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MissingPathVariableException.class)
	public ResponseEntity<String> handleMissingPathVariableException(MissingPathVariableException e)
	{
		return new ResponseEntity<String>("Variabile mancante nell'URL, inserisci la variabile faccia di culo!", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException e)
	{
		return new ResponseEntity<String>("Username gia presente nel sistema. Inserire un username valido", HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(CannotCreateTransactionException.class)
	public ResponseEntity<String> handleCannotCreateTransactionException(CannotCreateTransactionException e)
	{
		return new ResponseEntity<String>("Non sono riuscito a comunicare con il database. Credenziali errate.", HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleRuntimeException(RuntimeException e)
	{
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
