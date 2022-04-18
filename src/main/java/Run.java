

import javax.swing.*;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class Run {

		private static JFrame mainFrame = new JFrame();

	public static void main(String[] args) throws Exception {
		
			
		setHadoopHomeEnvironmentVariable();
		ProgressBar progressBar = new ProgressBar(mainFrame, true);
		progressBar.showProgressBar("Collecting Data...");
		UI ui = new UI();
		Executors.newCachedThreadPool().submit(() -> {
			try {
				ui.initUI();
			} finally {
				progressBar.setVisible(false);
				mainFrame.dispose();
			}
		});
	}

	private static void setHadoopHomeEnvironmentVariable() throws Exception {
		HashMap<String, String> hadoopEnvSetUp = new HashMap<>();
		hadoopEnvSetUp.put("HADOOP_HOME", new File("resources/winutils-master/hadoop-2.8.1").getAbsolutePath());
		Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
		Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
		theEnvironmentField.setAccessible(true);
		@SuppressWarnings("unchecked")
		Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
		env.clear();
		env.putAll(hadoopEnvSetUp);
		Field theCaseInsensitiveEnvironmentField = processEnvironmentClass
				.getDeclaredField("theCaseInsensitiveEnvironment");
		theCaseInsensitiveEnvironmentField.setAccessible(true);
		@SuppressWarnings("unchecked")
		Map<String, String> cienv = (Map<String, String>) theCaseInsensitiveEnvironmentField.get(null);
		cienv.clear();
		cienv.putAll(hadoopEnvSetUp);
	}
}
