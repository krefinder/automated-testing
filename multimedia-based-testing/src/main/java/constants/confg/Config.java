package constants.confg;

import java.io.File;

import constants.path.Path;
import utils.FileOperations;

public interface Config {
	
	/**VARIABLES**/

	//APP CONFIG DATA
	String PLATFORM_NAME = FileOperations.getConfigValue(Path.CONFIG_MOB_FILE_PATH, "platformName");
	String PLATFORM_VERSION = FileOperations.getConfigValue(Path.CONFIG_MOB_FILE_PATH, "platformVersion");
	String DEVICE_NAME = FileOperations.getConfigValue(Path.CONFIG_MOB_FILE_PATH, "deviceName");
	String DEVICE_UDID = FileOperations.getConfigValue(Path.CONFIG_MOB_FILE_PATH, "deviceUdid");
	String PORT_NUMBER = FileOperations.getConfigValue(Path.CONFIG_MOB_FILE_PATH, "portNumber");
	String WAIT_PACKAGE = FileOperations.getConfigValue(Path.CONFIG_MOB_FILE_PATH, "package");
	String WAIT_ACTIVITY = FileOperations.getConfigValue(Path.CONFIG_MOB_FILE_PATH, "activity");
	String PACKAGE = FileOperations.getConfigValue(Path.CONFIG_MOB_FILE_PATH, "actualPackage");
	String ACTIVITY = FileOperations.getConfigValue(Path.CONFIG_MOB_FILE_PATH, "actualActivity");
	String RECORD_TIME = FileOperations.getConfigValue(Path.CONFIG_MOB_FILE_PATH, "recordingTime");
	String SEARCH = FileOperations.getConfigValue(Path.CONFIG_MOB_FILE_PATH, "searchName");
	String USERNAME = FileOperations.getConfigValue(Path.CONFIG_MOB_FILE_PATH, "userName");
	String PASSWORD = FileOperations.getConfigValue(Path.CONFIG_MOB_FILE_PATH, "password");
	String APP = Path.PROJECT_PATH + File.separator + "app" + File.separator+ FileOperations.getConfigValue(Path.CONFIG_MOB_FILE_PATH, "app");
}
