package com.jbrown.core.events;

import java.util.EnumMap;

import com.jbrown.core.events.EventProvider.ProviderE;

public class BrownEventFactory {
	private EventProvider _providers;
	
	private static BrownEventFactory _factory;
	
	public static BrownEventFactory getInstance() {
		if(_factory == null){
			_factory = new BrownEventFactory();
		}
		
		return _factory;
	}
	
	private BrownEventFactory(){
		_providers = EventProvider.getInstance();
	}
	
	public BrownEventsI getPrimeEventHandler(){
		return _providers.getEventHandle(ProviderE.PRIME);
	}
	
	public BrownEventsI getAlternateEventHandler(){
		return _providers.getEventHandle(ProviderE.ALTERNATE);
	}
}
 

interface EventProviderI {
	enum ProviderE {
		PRIME, ALTERNATE;
	}

	BrownEventsI getEventHandle();
}

class EventProvider {
	private EnumMap<ProviderE, EventProviderI> _providers;
	private static EventProvider _instance;
	
	public enum ProviderE {
		PRIME("prime"), ALTERNATE("alternate");
		
		String name;
		ProviderE(String name){
			this.name = name;
		}
		
		public String getName(){
			return this.name;
		}
	};
	
	public static EventProvider getInstance() {
		if (_instance == null) {
			_instance = new EventProvider();
		}

		return _instance;
	}
	
	private EventProvider(){
		_providers = new EnumMap<ProviderE, EventProviderI>(ProviderE.class);
		_providers.put(ProviderE.PRIME, PrimeEventProvider.getInstance());
		_providers.put(ProviderE.ALTERNATE, AlternateEventProvider.getInstance());
	}
	
	public BrownEventsI getEventHandle(ProviderE e){
		EventProviderI provider = null;
		
		switch (e) {
			case PRIME:
				provider = _providers.get(ProviderE.PRIME);
				break;
			case ALTERNATE:
				provider = _providers.get(ProviderE.ALTERNATE);
				break;
			default:
				provider = _providers.get(ProviderE.ALTERNATE);
				break;
		}
		
		if(provider != null){
			return provider.getEventHandle();
		}
		
		throw new RuntimeException(String.format(
				"NO Event Provider Found!! Avilavle Providers are: [%s, %s]",
				ProviderE.PRIME.getName(), ProviderE.ALTERNATE.getName()));
	}
}

class PrimeEventProvider implements EventProviderI {
	private BrownEventsI _brownEvent;
	
	private static PrimeEventProvider _primeEvent;
	
	public static PrimeEventProvider getInstance(){
		if(_primeEvent == null){
			_primeEvent = new PrimeEventProvider();
		}
		
		return _primeEvent;
	}
	
	private PrimeEventProvider(){
		_brownEvent = new PrimeBrownEventListener();
	}

	@Override
	public BrownEventsI getEventHandle() {
		return _brownEvent;
	}
}

class AlternateEventProvider implements EventProviderI  {
	private BrownEventsI _brownEvent;
	
	private static AlternateEventProvider _alternativeEvent;
	
	public static AlternateEventProvider getInstance(){
		if(_alternativeEvent == null){
			_alternativeEvent = new AlternateEventProvider();
		}
		
		return _alternativeEvent;
	}
	
	private AlternateEventProvider(){
		_brownEvent = new AlternativeBrownEventListener();
	}
	
	@Override
	public BrownEventsI getEventHandle() {
		return _brownEvent;
	}	
}
