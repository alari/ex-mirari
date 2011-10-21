@Typed package mirari.util

import org.apache.log4j.Logger
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class ConfigReader {

	Properties config

	private static Logger log = Logger.getLogger(ConfigReader)

  ConfigReader(){
    config = ConfigurationHolder.config.toProperties()
  }

	ConfigReader(ConfigObject configObject) {
		config = configObject.toProperties()
	}

	public read(key, defaultValue = null) {
		config[key] ?: defaultValue
	}
}