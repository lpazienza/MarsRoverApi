package tdd.training.tests;

import tdd.training.session1.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MarsRoverTest {
	MarsRover rover1st;
	
	@Before
	public void setUp() throws MarsRoverException{
		rover1st = new MarsRover(100,100,"");
	}
	

	@Test(expected = MarsRoverException.class)
	public void testsCostructorWithNegativeInput() throws MarsRoverException {
		//arrange
		@SuppressWarnings("unused")
		MarsRover roverTest = new MarsRover(-1,2,"");
	}
	
	@Test
	public void hasSplittedCorrectSequenceCommand() throws MarsRoverException {
		//Arrange
		char[] sequence = {'f','f','r','f','f'};
		//act & assert
		for(int i = 0 ; i<4 ; i++){
		assertEquals(sequence[i],rover1st.splittedCommandSequence("ffrff")[i]);
		}
	}
	
	@Test(expected = MarsRoverException.class)
	public void SequenceCommandException() throws MarsRoverException {
		//act
		rover1st.executeCommand("ffrtb");
	}
	
	@Test
	public void hasCorrectPositionAfterSomeCommands() throws MarsRoverException{
		//act
		rover1st.executeCommand("ffrffflb");
		//assert
		assertEquals("(3,1)",rover1st.getPosition().toString());
	}
	
	@Test
	public void hasCorrectSpawnPosition() throws MarsRoverException{
		//Arrange
		MarsRover rover2nd = new MarsRover(3,3,"");
		//act
		rover2nd.executeCommand("ffrfffrff");
		//assert
		assertEquals("(0,0)",rover2nd.getPosition().toString());
	}
	
	@Test
	public void hasCorrectOutput() throws MarsRoverException{
		//act & assert
		assertEquals("(0,2,N)",rover1st.executeCommand("ffrlfb"));
		
	}
	
	@Test
	public void hasCorrectObstaclesPosition() throws MarsRoverException{
		//Arrange
		MarsRover rover2nd = new MarsRover(70,70,"(5,6)(25,65)(14,23)(14,69)");
		//act & assert
		assertEquals("(5,6)",rover2nd.computeObstacles().get(0).toString());
		assertEquals("(25,65)",rover2nd.computeObstacles().get(1).toString());
		assertEquals("(14,23)",rover2nd.computeObstacles().get(2).toString());
		assertEquals("(14,69)",rover2nd.computeObstacles().get(3).toString());
		assertEquals(4,rover2nd.computeObstacles().size());
	}
	
	@Test (expected = MarsRoverException.class)
	public void throwsExceptionWithNegativeObstaclesPosition() throws MarsRoverException{
		//Arrange
		MarsRover rover2nd = new MarsRover(20,20,"(5,6)(-8,8)");
		//act (throws exception)
		rover2nd.computeObstacles();

	}
	
	@Test (expected = MarsRoverException.class)
	public void throwsExceptionWithObstaclesPositionOverTheEdge() throws MarsRoverException{
		//Arrange
		MarsRover rover2nd = new MarsRover(20,20,"(5,45)(8,8)");
		//act (throws exception)
		rover2nd.computeObstacles();

	}
	
	@Test
	public void hasCorrectPositionWithObstacles() throws MarsRoverException{
		//Arrange
		MarsRover rover3rd = new MarsRover(20,20,"(5,5)(1,3)(2,4)");
		//act
		rover3rd.executeCommand("rflfffffffflff");//incontra l'ostacolo in (1,3) e non va avanti
		//assert
		assertEquals("(19,2)",rover3rd.getPosition().toString());
	}
	
	@Test
	public void hasCorrectOutputWithObstaclesFound() throws MarsRoverException{
		//Arrange
		MarsRover rover3rd = new MarsRover(20,20,"(5,5)(1,3)(2,4)");
		//act & arrange
		assertEquals("(2,2,E)(1,3)(2,4)",rover3rd.executeCommand("fffrffflrrflfflffrfbbfrflb"));
	}
	
	
}
