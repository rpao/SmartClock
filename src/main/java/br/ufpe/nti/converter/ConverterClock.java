package br.ufpe.nti.converter;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.nti.model.Clock;
import br.ufpe.nti.model.SentClock;

public class ConverterClock {
	public static SentClock converter (Clock clock){
		SentClock novoClock = new SentClock();
		novoClock.setId(clock.getId());
		novoClock.setCreatedAt(clock.getCreatedAt());
		novoClock.setTime(clock.getTime());
		novoClock.setAngle();
		return novoClock;
	}
	public static List<SentClock> converter(List<Clock>clockhistory){
		List<SentClock> lista = new ArrayList<SentClock>();

		for (int i = 0; i < clockhistory.size(); i++){
			lista.add(converter(clockhistory.get(i)));
		}
		
		return lista;
	}
}
