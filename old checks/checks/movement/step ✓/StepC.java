package guard.check.checks.movement.step;

import guard.check.GuardCategory;
import guard.check.GuardCheck;
import guard.check.GuardCheckInfo;
import guard.check.GuardCheckState;
import guard.exempt.ExemptType;
import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;

@GuardCheckInfo(name = "Step C", category = GuardCategory.Movement, state = GuardCheckState.STABLE, addBuffer = 1, removeBuffer = 1, maxBuffer = 4)
public class StepC extends GuardCheck {

    public void onMove(PacketPlayReceiveEvent packet, double motionX, double motionY, double motionZ, double lastMotionX, double lastMotionY, double lastMotionZ, float deltaYaw, float deltaPitch, float lastDeltaYaw, float lastDeltaPitch) {
        boolean exempt = isExempt(ExemptType.FLYING, ExemptType.SLIME, ExemptType.TELEPORT, ExemptType.JOINED, ExemptType.INSIDE_VEHICLE, ExemptType.NEAR_VEHICLE, ExemptType.CLIMBABLE, ExemptType.LIQUID, ExemptType.STAIRS, ExemptType.SLAB, ExemptType.WEB);
        if(lastMotionY - motionY < 0.01)  {
            if(!exempt && motionY != 0 && !gp.onLowBlock && !gp.nearBerry && motionY > -3.45) fail(packet, "Repeated motion", "lmY §9" + lastMotionY + "\n" + " §8»§f mY §9" + motionY);
        }
        if(lastMotionY - motionY > 0.01) removeBuffer();
    }

}
