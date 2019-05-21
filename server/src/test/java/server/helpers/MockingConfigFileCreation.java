package server.helpers;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import server.Properties;
import server.User;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MockingConfigFileCreation {
	
	public static void mockingConfigFileCreation(User user) {
		Properties properties = mock(Properties.class);
		PropertiesForTesting propertiesForTesting = new PropertiesForTesting();
		propertiesForTesting.createConfigFileForTesting();
		
		when(user.getProperties()).thenReturn(properties);
		
		doAnswer(new Answer<String>() {
			@Override
			public String answer(InvocationOnMock invocationOnMock) throws Throwable {
				String propName = invocationOnMock.getArgument(0).toString();
				return propertiesForTesting.loadConfigFileForTesting(propName);
			}
		}).when(properties).loadConfigFile(any(String.class));
	}
}
