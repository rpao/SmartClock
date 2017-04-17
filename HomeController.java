package br.ufpe.nti.controller;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;

import javax.ws.rs.PathParam;

import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpe.nti.controller.repository.ClockHistoryRepository;
import br.ufpe.nti.converter.ConverterClock;
import br.ufpe.nti.model.Clock;
import br.ufpe.nti.model.SentClock;

@RestController
@EnableAutoConfiguration
public class HomeController {

	@Autowired
	ClockHistoryRepository chR = new ClockHistoryRepository();

	@RequestMapping(value = {"/clock"}, method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<String> getClock(){	
		SentClock clock = new SentClock();
		clock.setId((long)1);
		clock.setTime(LocalTime.now());
		clock.setCreatedAt(new Timestamp(11111111));
		clock.setAngle();

		final HttpHeaders httpHeaders= new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		ObjectMapper mapper = new ObjectMapper();

		try {
			String jsonString = mapper.writeValueAsString(clock);
			return new ResponseEntity<String>(jsonString,httpHeaders, HttpStatus.OK);
		} catch (JsonProcessingException e) {
			System.out.println("ERRO: "+e.getMessage()+"\n\n");
			String jsonString = "";
			return new ResponseEntity<String>(jsonString,httpHeaders, HttpStatus.OK);
		}
	}

	@RequestMapping(value = {"/clockhistory"}, method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<String> getClockHistory(){
		final HttpHeaders httpHeaders= new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		ObjectMapper mapper = new ObjectMapper();		
		try{
			List<Clock> clockhistory = chR.listAll();
			List<SentClock> sentClockhistory  = ConverterClock.converter(clockhistory);
			String jsonString = mapper.writeValueAsString(sentClockhistory);
			return new ResponseEntity<String>(jsonString,httpHeaders, HttpStatus.OK);

		}catch (Exception e){
			System.out.print(e.getMessage());
			String jsonString = "";
			return new ResponseEntity<String>(jsonString,httpHeaders, HttpStatus.OK);
		}
	}

	@RequestMapping(value = {"/clockhistory/{idHistoryClock}"}, method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.ALL_VALUE)
	public ResponseEntity<String> getClockHistoryByID(@PathParam("idHistoryClock")String idHistoryClock){
		final HttpHeaders httpHeaders= new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);		
		ObjectMapper mapper = new ObjectMapper();
		try {
			Clock clock = chR.findByID(idHistoryClock);
			String jsonString = mapper.writeValueAsString(clock);
			return new ResponseEntity<String>(jsonString,httpHeaders, HttpStatus.OK);
		}
		catch (Exception e) {
			System.out.println("ERRO: "+e.getMessage()+"\n\n");
			String jsonString = "Nenhum Clock com o ID informado foi encontrado no Banco de Dados";
			return new ResponseEntity<String>(jsonString,httpHeaders, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = {"/clock"} , method = {RequestMethod.POST}, produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> postClock(@RequestBody String time) throws JSONException{
		final HttpHeaders httpHeaders= new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		try{
		JSONObject timeJson = new JSONObject(time);

		Clock clock = new Clock();
		clock.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		clock.setTime(LocalTime.parse(((String)timeJson.get("time")).replace(" ", "")));

		ObjectMapper mapper = new ObjectMapper();

			try{
				clock = chR.save(clock);
			}catch(Exception e){
				System.out.println("ERRO: "+e.getMessage()+"\n\n");
				String jsonString = "";
				return new ResponseEntity<String>(jsonString,httpHeaders, HttpStatus.OK);
			}
			SentClock sentClock = ConverterClock.converter(clock);
			String jsonString = mapper.writeValueAsString(sentClock);
			return new ResponseEntity<String>(jsonString,httpHeaders, HttpStatus.OK);

		} catch (JsonProcessingException e) {
			System.out.println("ERRO: "+e.getMessage()+"\n\n");
			String jsonString = "Nenhum Clock encontrado com o ID informado";
			return new ResponseEntity<String>(jsonString,httpHeaders, HttpStatus.OK);
		}
	}
}