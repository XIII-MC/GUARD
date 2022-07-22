package guard.check.checks.movement.jump;

import guard.check.GuardCategory;
import guard.check.GuardCheck;
import guard.check.GuardCheckInfo;
import guard.check.GuardCheckState;
import guard.exempt.ExemptType;
import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;
import org.bukkit.potion.PotionEffectType;

@GuardCheckInfo(name = "Jump A", category = GuardCategory.Movement, state = GuardCheckState.Testing, addBuffer = 0, removeBuffer = 0, maxBuffer = 0)
public class JumpA extends GuardCheck {


    public void onMove(PacketPlayReceiveEvent packet, double motionX, double motionY, double motionZ, double lastMotionX, double lastMotionY, double lastMotionZ, float deltaYaw, float deltaPitch, float lastDeltaYaw, float lastDeltaPitch) {
        boolean step = mathOnGround(motionY) && mathOnGround(gp.from.getY());
        boolean jumped = motionY > 0 && gp.from.getY() % (1D/64) == 0 && !gp.playerGround && !step;
        double expectedJumpMotion = 0.42F + (double)(gp.hasPotionEffect(PotionEffectType.JUMP) ? (gp.getEffectByType(PotionEffectType.JUMP).get().getAmplifier() + 1) * 0.1F : 0);
        boolean exempt = isExempt(ExemptType.INSIDE_VEHICLE, ExemptType.VELOCITY, ExemptType.CLIMBABLE, ExemptType.FLYING, ExemptType.SLIME, ExemptType.BLOCK_ABOVE, ExemptType.PISTON, ExemptType.LIQUID, ExemptType.NEAR_VEHICLE, ExemptType.TELEPORT, ExemptType.WEB, ExemptType.TRAPDOOR);
        if (jumped && !exempt) {
            if (motionY < expectedJumpMotion) {
                fail(packet, "Jump was lower than expected", "y=" + motionY + " expected=" + expectedJumpMotion);
            }
        }
    }
}
