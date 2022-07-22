package guard.check.checks.movement.fly;

import guard.check.GuardCategory;
import guard.check.GuardCheck;
import guard.check.GuardCheckInfo;
import guard.check.GuardCheckState;
import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;

@GuardCheckInfo(name = "Fly A", category = GuardCategory.Movement, state = GuardCheckState.Testing, addBuffer = 1, removeBuffer = 0.5, maxBuffer = 1)
public class FlyA extends GuardCheck {

    public void onMove(PacketPlayReceiveEvent packet, double motionX, double motionY, double motionZ, double lastMotionX, double lastMotionY, double lastMotionZ, float deltaYaw, float deltaPitch, float lastDeltaYaw, float lastDeltaPitch) {
        double predictedMotionY = (lastMotionY - 0.08D) * (double)0.98F;
        double diff = Math.abs(predictedMotionY - motionY);
        if(diff > 0.0000000000004 && gp.isInAir()) {
            fail(packet, "Predictions unfollowed", "move=" + motionY + " predicted=" + predictedMotionY);
        }else removeBuffer();
    }
}
