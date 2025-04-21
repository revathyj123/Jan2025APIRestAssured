package CreateWithPOJOAndLombok;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//This class is just for demo of Lombok class - used in CreateAnUserWithLombokTest.java

@Builder
@Data //generates getter and setter methods for the private variables 
@AllArgsConstructor //Generates an all arg constructor
@NoArgsConstructor //Generates No Arg constructor
public class UserLombok {

	private String name;
	private String gender;
	private String email;
	private String status;
}
