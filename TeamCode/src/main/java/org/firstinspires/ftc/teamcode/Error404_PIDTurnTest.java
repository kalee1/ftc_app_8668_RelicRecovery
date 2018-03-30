package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Gyro Turn Test", group="Jewel")

/**
 * Error404_PIDTurnTest extends <code>Error404_Hardware_Tier2</code> and contains the state
 * machine that tests the PID based gyro turn.
 *
 * @author Team 8668
 * @see Error404_Hardware_Tier2
 */
public class Error404_PIDTurnTest extends Error404_Hardware_Tier2
{
    //////////////////////////////////////////////////////////////////////////////////
    /** This int variable sets the starting case value for the state machine (which step in the sequence to start with -- i.e. the first one or case 0) */
    private int state = 0;
    private int encoder=0;
    private double timer=0;
    private String reason = new String("None");
    /**  */
    protected MiniPID turnControl;

    /** When the driver hits start sets up PID control. */
    @Override public void start()
    {
        double Kc = 0.1;
        double Pc = 0.5;
        double Kp = 0.6*Kc;
        turnControl = new MiniPID( Kc, 0, 0 );
//        turnControl = new MiniPID(0.5*Kc, 2*Kp/Pc, 0.125*Kp*Pc);
        turnControl.setOutputLimits(1.0);
//        turnControl.setOutputRampRate(0.25);
        turnControl.reset();
        super.start();
    }


   /** Gets the gyro heading, sets the jewel servos to the home poition, and grabs the encoder
    * value of the leftFront drive motor for navigation purposes. */
    @Override public void init(){
        super.init();
        telemetry.addData("Gyro: ", getHeading());
        telemetry.addData("","V 1");
        encoder=leftFront.getCurrentPosition();

        encoder=leftFront.getCurrentPosition();
    }


    /**
     * Uses PID control to turn to a particular heading.
     *
     * @param targetHeading specifies the direction the robot needs to face
     */
    public boolean pointTurnGyro( double targetHeading )
    {
        boolean done = false;
        double currentHeading = getHeadingDbl();
        double motor_power = 0;

        if ( Math.abs( targetHeading ) > 180.0 )
        {
            // Reject any targetHeadings outside of the allowable rage of the gyro (+/- 180 degrees)
            done = true;
        }
        else
        {
            motor_power = turnControl.getOutput( currentHeading, targetHeading );
            pointTurnCombo( motor_power );

            if ( ( Math.abs(currentHeading - targetHeading) < 1.0 ) &&
                    ( motor_power < 0.05 ) )
            {
                done = true;
            }
        }

        return done;
    }





    //////////////////////////////////////////////////////////////////////////////////
    /** Contains the state machine which contains the structure for each move the robot will take
     * during autonomous. In the cases where the robot will drive somewhere, there are no values
     * hard-coded in as these values will change depending on what quadrant of the field the robot
     * is starting in. Instead the Autonomous class sources the quadrant-specific movement values
     * from field position-dependant child classes. */
    @Override public void loop ()
    {
        switch (state)
        {
            case 0:
                pointTurnGyro( 90.0 );
                timer = getRuntime();
                state++;
                break;
            case 1:  //Turn 90 degress
                if ( pointTurnGyro( 90.0) )
                {
                    state++;
                    reason = "Turn Complete";
                }
                if ( getRuntime() - timer > 5.0 )
                {
                    state++;
                    reason = "Timeout";
                }

                break;
            default:
                stopEverything();
                turnControl.reset();
                break;


        }
        telemetry.addData("1. State: ", state);
        telemetry.addData("2. Gyro: ", getHeadingDbl());
        telemetry.addData("3. Left Front Position: ", leftFront.getCurrentPosition());
        telemetry.addData("4. Delta Position: ", encoder);
        telemetry.addData("5. Reason for stop: ", reason );




    } // loop
} //
