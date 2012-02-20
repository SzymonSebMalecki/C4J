package de.andrena.next.acceptancetest.s7a;

import org.apache.log4j.Level;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.andrena.next.acceptancetest.floatingwindow.NorthEastAndSouthWestFloatingWindow;
import de.andrena.next.acceptancetest.floatingwindow.NorthEastFloatingWindowSpec;
import de.andrena.next.acceptancetest.floatingwindow.Vector;
import de.andrena.next.acceptancetest.workingstudent.WorkingStudent;
import de.andrena.next.acceptancetest.workingstudent.YoungWorkingStudent;
import de.andrena.next.systemtest.TransformerAwareRule;

public class NNPreS7aTest {

	@Rule
	public TransformerAwareRule transformerAware = new TransformerAwareRule();

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private NorthEastFloatingWindowSpec northEastFloatingWindow;
	private WorkingStudent workingStudent;
	private YoungWorkingStudent youngWorkingStudent;

	@Test
	public void testErrorByMultipleInheritance() {
		thrown.expect(AssertionError.class);
		thrown.expectMessage("vector.x > 0");
		northEastFloatingWindow = new NorthEastAndSouthWestFloatingWindow(new Vector(0, 0), 200, 200);
		//Move window to SouthWest 
		northEastFloatingWindow.move(new Vector(-2, -5));
	}

	@Test
	public void testWarningMessageByStrengtheningOfPrecondition() {
		transformerAware
				.expectGlobalLog(
						Level.WARN,
						"found strengthening pre-condition in de.andrena.next.acceptancetest.workingstudent.YoungWorkingStudentContract.setAge(int)"
								+ " which is already defined from de.andrena.next.acceptancetest.workingstudent.StudentSpecContract - ignoring the pre-condition");

		youngWorkingStudent = new YoungWorkingStudent();
		youngWorkingStudent.setAge(60);
	}

	@Test
	public void testMultipleInheritanceOk_PreTrue() {
		workingStudent = new WorkingStudent();
		workingStudent.setAge(99);
	}

	@Test
	public void testMultipleInheritanceOk_PreFalse() {
		thrown.expect(AssertionError.class);
		thrown.expectMessage("age < 100");

		workingStudent = new WorkingStudent();
		workingStudent.setAge(101);
	}

}
