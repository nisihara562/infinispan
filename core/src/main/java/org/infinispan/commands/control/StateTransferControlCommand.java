package org.infinispan.commands.control;

import org.infinispan.commands.ReplicableCommand;
import org.infinispan.context.InvocationContext;
import org.infinispan.remoting.RpcManager;

/**
 * A command that informs caches participating in a state transfer of the various stages in the state transfer process.
 *
 * @author Manik Surtani
 * @since 4.0
 */
public class StateTransferControlCommand implements ReplicableCommand {
   public static final int COMMAND_ID = 49;
   RpcManager rpcManager;
   boolean enabled;

   public StateTransferControlCommand() {
   }

   public StateTransferControlCommand(boolean enabled) {
      this.enabled = enabled;
   }

   public void init(RpcManager rpcManager) {
      this.rpcManager = rpcManager;
   }

   public Object perform(InvocationContext ctx) throws Throwable {
      if (enabled)
         rpcManager.getTransport().getDistributedSync().acquireSync();
      else
         rpcManager.getTransport().getDistributedSync().releaseSync();
      return null;
   }

   public byte getCommandId() {
      return COMMAND_ID;
   }

   public Object[] getParameters() {
      return new Object[]{enabled};
   }

   public void setParameters(int commandId, Object[] parameters) {
      enabled = (Boolean) parameters[0];
   }

   @Override
   public String toString() {
      return "StateTransferControlCommand{" +
            "enabled=" + enabled +
            '}';
   }
}
