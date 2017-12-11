package br.uece.justsettings.util;

import java.util.ArrayList;

import br.uece.justsettings.persistence.web.WebActionConfig;
import br.uece.justsettings.persistence.web.WebAggregationConfig;
import br.uece.justsettings.persistence.web.WebCompositionConfig;
import br.uece.justsettings.persistence.web.WebEntityConfig;
import br.uece.justsettings.settings.JBConfig;
import br.uece.justsettings.settings.ParametroConfig;
import br.uece.justsettings.settings.stream.StreamAttributeConfig;
import br.uece.justsettings.settings.stream.StreamElementConfig;
import br.uece.justsettings.settings.stream.StreamEntityConfig;
import br.uece.justsettings.settings.stream.StreamEnumeratedConfig;
import br.uece.justsettings.settings.stream.StreamTemporalConfig;
import br.uece.justsettings.settings.stream.StreamTransientConfig;
import br.uece.justsettings.settings.ui.JBActionConfig;
import br.uece.justsettings.settings.ui.JBAttributeConfig;
import br.uece.justsettings.settings.ui.JBDescriptionConfig;
import br.uece.justsettings.settings.ui.JBEntityConfig;
import br.uece.justsettings.settings.ui.JBEnumerationConfig;
import br.uece.justsettings.settings.ui.JBLargeConfig;
import br.uece.justsettings.settings.ui.JBTemporalConfig;

public class ConfigFactory {

	private static ConfigFactory instance;
	
	private ConfigFactory() {
	}
	

	public static ConfigFactory getInstance() {
        if (instance == null) {
            instance = new ConfigFactory();
        }
        return instance;
    }
	
	public JBConfig getConfig(String nomeConfig) {
    	JBConfig retorno = null;
    	
    	switch (nomeConfig) {
    	
    	// INTERFACE
    	case "JBEntity":
    		retorno = new JBEntityConfig();
    		break;
    	case "JBEnumeration":
    		retorno = new JBEnumerationConfig();
    		break;
    	case "JBAttribute":
    		retorno = new JBAttributeConfig();
    		break;
    	case "JBLarge":
    		retorno = new JBLargeConfig();
    		break;
    	case "JBTemporal":
    		retorno = new JBTemporalConfig();
    		break;
    	case "JBDescription":
    		retorno = new JBDescriptionConfig();
    		break;
    	case "JBAction":
    		retorno = new JBActionConfig();
    		break;
    		
    		
    	// PERSISTENCIA
    		
    	case "WebEntity":
    		retorno = new WebEntityConfig();
    		break;
    	case "WebAggregation":
    		retorno = new WebAggregationConfig();
    		break;
    	case "WebComposition":
    		retorno = new WebCompositionConfig();
    		break;
    	case "WebAction":
    		retorno = new WebActionConfig();
    		break;
    	
    		
    		
		// STREAM
    	case "StreamEntity":
    		retorno = new StreamEntityConfig();
    		break;
    	case "StreamAttribute":
    		retorno = new StreamAttributeConfig();
    		break;
    	case "StreamElement":
    		retorno = new StreamElementConfig();
    		break;
    	case "StreamEnumerated":
    		retorno = new StreamEnumeratedConfig();
    		break;
    	case "StreamTemporal":
    		retorno = new StreamTemporalConfig();
    		break;
    	case "StreamTransient":
    		retorno = new StreamTransientConfig();
    		break;
    		

    	}
    	
    	return retorno;
		
	}
    
    public ArrayList<ParametroConfig> getParametrosConfig(String nomeConfig) {
    	
    	ArrayList<ParametroConfig> retorno = new ArrayList<>();
    	
    	switch (nomeConfig) {
    	
    	// INTERFACE
    	case "JBEntity":
    		retorno = new JBEntityConfig().getParametros();
    		break;
    	case "JBEnumeration":
    		retorno = new JBEnumerationConfig().getParametros();
    		break;
    	case "JBAttribute":
    		retorno = new JBAttributeConfig().getParametros();
    		break;
    	case "JBLarge":
    		retorno = new JBLargeConfig().getParametros();
    		break;
    	case "JBTemporal":
    		retorno = new JBTemporalConfig().getParametros();
    		break;
    	case "JBDescription":
    		retorno = new JBDescriptionConfig().getParametros();
    		break;
    	case "JBAction":
    		retorno = new JBActionConfig().getParametros();
    		break;
    		
    		
    	// PERSISTENCIA
    		
    	case "WebEntity":
    		retorno = new WebEntityConfig().getParametros();
    		break;
    	case "WebAggregation":
    		retorno = new WebAggregationConfig().getParametros();
    		break;
    	case "WebComposition":
    		retorno = new WebCompositionConfig().getParametros();
    		break;
    	case "WebAction":
    		retorno = new WebActionConfig().getParametros();
    		break;
    	
    		
    		
		// STREAM
    	case "StreamEntity":
    		retorno = new StreamEntityConfig().getParametros();
    		break;
    	case "StreamAttribute":
    		retorno = new StreamAttributeConfig().getParametros();
    		break;
    	case "StreamElement":
    		retorno = new StreamElementConfig().getParametros();
    		break;
    	case "StreamEnumerated":
    		retorno = new StreamEnumeratedConfig().getParametros();
    		break;
    	case "StreamTemporal":
    		retorno = new StreamTemporalConfig().getParametros();
    		break;
    	case "StreamTransient":
    		retorno = new StreamTransientConfig().getParametros();
    		break;
    		

    	}
    	
    	return retorno;
    }

}
