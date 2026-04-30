import java.util.List;

/**
 * Handles the collsion aspect of the game.
 */
public class CollisionEngine {

    private static final int MAX_BOUNCES_PER_FRAME = 3;
    private static final double ANTI_STICK_PUSH_DISTANCE = 0.001;
    private static final double IMPOSSIBLE_COLLISION_FRACTION_TEMP = 2.0; // any value > 1.0 works, since we only
                                                                          // consider fractions between 0 and 1
    private static final double MIN_TIME_REMAINING_THRESHOLD = 0.001; // to prevent infinite loops

    /**
     * Resolves the collision between a ball and a lines .
     * 
     * 
     * @param ball the ball
     * @param wall the wall
     */

    public static void resolveBallLineCollision(Ball ball, Line[] wallArray) {

        double frameTimeRemaining = 1.0;
        int bounceCountThisFrame = 0;
        double ballRadius = ball.getSize();

        while (frameTimeRemaining > MIN_TIME_REMAINING_THRESHOLD && bounceCountThisFrame < MAX_BOUNCES_PER_FRAME) {

            // variables for the closest wall to the ball.
            // i set fractionOfFrameToClosestCollision
            // to IMPOSSIBLE_COLLISION_FRACTION_TEMP
            // because it is impossible to have a collision after the end of the frame (1.0)
            // nd this way we can easily check if we found a collision or not.
            double fractionOfFrameToClosestCollision = IMPOSSIBLE_COLLISION_FRACTION_TEMP;
            Line closestWallHit = null;
            double closestWallNormalX = 0;
            double closestWallNormalY = 0;

            double ballStartPosX = ball.getPoint().getX();
            double ballStartPosY = ball.getPoint().getY();

            // speed
            double ballVx = ball.getVelocity().getDx() * frameTimeRemaining;
            double ballVy = ball.getVelocity().getDy() * frameTimeRemaining;

            // end position of the ball if no collisions occur in this step
            double ballIntendedEndX = ballStartPosX + ballVx;
            double ballIntendedEndY = ballStartPosY + ballVy;

            for (Line wall : wallArray) {
                // wall pos
                double wallStartX = wall.start().getX();
                double wallStartY = wall.start().getY();
                double wallEndX = wall.end().getX();
                double wallEndY = wall.end().getY();

                // wall directoin
                double wallDirectionX = wallEndX - wallStartX;
                double wallDirectionY = wallEndY - wallStartY;

                double wallLength = Math.sqrt(wallDirectionX * wallDirectionX + wallDirectionY * wallDirectionY);

                if (wallLength < GameConstants.EPSILON) {
                    continue; // skip zero-length walls
                }

                // get wall normal
                double wallNormalX = -wallDirectionY / wallLength;
                double wallNormalY = wallDirectionX / wallLength;

                // makes sure the normal is facing towards the ball and not thwe wrong direction
                double vectorFromWallToBallX = ballStartPosX - wallStartX;
                double vectorFromWallToBallY = ballStartPosY - wallStartY;
                if ((wallNormalX * vectorFromWallToBallX + wallNormalY * vectorFromWallToBallY) < 0) {
                    wallNormalX = -wallNormalX;
                    wallNormalY = -wallNormalY;
                }

                // create an ofest wall for easier calc
                double offsetWallStartX = wallStartX + (wallNormalX * ballRadius);
                double offsetWallStartY = wallStartY + (wallNormalY * ballRadius);
                double offsetWallEndX = wallEndX + (wallNormalX * ballRadius);
                double offsetWallEndY = wallEndY + (wallNormalY * ballRadius);

                // the calc for line segment intersection of
                // Ball Path with Offset Wall
                // calc denominator
                double intersectionDenominator = (ballStartPosX - ballIntendedEndX)
                        * (offsetWallStartY - offsetWallEndY) -
                        (ballStartPosY - ballIntendedEndY) * (offsetWallStartX - offsetWallEndX);

                // If denominator is in the Epslion range the lines are parallel and will not
                // meet
                if (Math.abs(intersectionDenominator) < GameConstants.EPSILON) {
                    continue;
                }

                // 't' (hitFractionAlongBallPath) represents the percentage of the movement path
                // completed at impact
                double hitFractionAlongBallPath = ((ballStartPosX - offsetWallStartX)
                        * (offsetWallStartY - offsetWallEndY) -
                        (ballStartPosY - offsetWallStartY) * (offsetWallStartX - offsetWallEndX))
                        / intersectionDenominator;

                // 'u' (hitFractionAlongWall) represents where the hit occurred on the wall
                // segment itself
                double hitFractionAlongWall = ((ballStartPosX - ballIntendedEndX) * (ballStartPosY - offsetWallStartY) -
                        (ballStartPosY - ballIntendedEndY) * (ballStartPosX - offsetWallStartX))
                        / intersectionDenominator;

                // now we check if is valid colllsion
                // hitFractionAlongBallPath and hitFractionAlongWall
                // must be between 0 and 1 so it happen in this frame
                // and the collison happens on the finite line
                if (hitFractionAlongBallPath >= 0 && hitFractionAlongBallPath <= 1 &&
                        hitFractionAlongWall >= 0 && hitFractionAlongWall <= 1) {

                    // If valid, check if this is the EARLIEST collision in this frame (the minimum
                    // 't')
                    if (hitFractionAlongBallPath < fractionOfFrameToClosestCollision) {
                        fractionOfFrameToClosestCollision = hitFractionAlongBallPath;
                        closestWallHit = wall;
                        closestWallNormalX = wallNormalX;
                        closestWallNormalY = wallNormalY;
                    }
                }

            }
            // no collsion
            if (closestWallHit == null) {
                ball.setCenter(ballIntendedEndX, ballIntendedEndY);
                break;
            }

            // if collsion happend
            // we calculate impact point
            double impactPositionX = ballStartPosX + (ballVx * fractionOfFrameToClosestCollision);
            double impactPositionY = ballStartPosY + (ballVy * fractionOfFrameToClosestCollision);

            // Add a tiny push (Epsilon) along the normal to prevent the ball from getting
            // stuck
            // inside the wall segment on the next iteration due to floating-point
            // inaccuracies.
            double finalPosX = impactPositionX + (closestWallNormalX * ANTI_STICK_PUSH_DISTANCE);
            double finalPosY = impactPositionY + (closestWallNormalY * ANTI_STICK_PUSH_DISTANCE);
            ball.setCenter(finalPosX, finalPosY);

            // in the feture could implemnt smarter speed
            ball.setVelocity(0 - ball.getVelocity().getDx(), 0 - ball.getVelocity().getDy());
            // calac the time spent reaching this wall and do decut from 1;
            frameTimeRemaining = frameTimeRemaining * (1.0 - fractionOfFrameToClosestCollision);
            bounceCountThisFrame++;
        }

    }
}
