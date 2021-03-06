package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Red Front", group="Jewel")

/**
 * e404_FrontRed extends <code>Error404AutonomousFront</code> class and has the encoder distances for this quadrant.
 *
 * @author Team 8668
 * @See Error404AutonomousFront
 * */
public class e404_FrontRed extends Error404AutonomousFront

{
    ///////////////////////////////////////////////////////////////////

    /** The quadrant the robot is in. */
    public e404_FrontRed()
    {
        setLocation("FRONT", "RED");
    }

    /** Setting the movement distances for this quadrant. */
    @Override public void init()
    {
        //Drive moves
        cryptoboxDriveDistance = 435;
        backToCryptobox=470;
        driveToPile=313;
        cryptoboxSlide=0;
        slideBackToCryptobox=0;
        slideAwayFromTheCryptobox=0;


        //turns
        turnToPile=85;
        turnToCryptobox=-85;

        useExtendedGyro = false;

        setMultipleDirections("straight", "forward");
        super.init();  //super.init() method is moved to bottom to not get in the way of the driveStraight() method
    }

    /** Overriding the start method to provide a place to put things for when this class is selected. */
    @Override public void start(){
        super.start();
    }

    /**
     * Reads the pictograph and uses the location to select the correct values for the robot movement.
     *
     * @param cryptoboxKey  records the pictograph key as a string */
    @Override protected boolean updateFromVuforia(String cryptoboxKey)
    {
        boolean result = false;

        if(cryptoboxKey.equals("LEFT"))
        {
            cryptoboxDriveDistance = 550;
            result = true;
        }
        else if(cryptoboxKey.equals("RIGHT"))
        {
            cryptoboxDriveDistance=340;
            result = true;
        }
        else if(cryptoboxKey.equals("CENTER"))
        {
            // REAL CENTER VALUE //
           cryptoboxDriveDistance=445;

            result = true;
        }
        return result;
    }

    /** Not used in this class. */
    @Override public void loop ()
    {
        super.loop();
    } // loop

}
