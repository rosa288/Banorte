package utils;

import java.time.Duration;

public class Sleep {
	
	public static void seconds(int time) {
		try {
			
			Thread.sleep(Duration.ofSeconds(time));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

} 
