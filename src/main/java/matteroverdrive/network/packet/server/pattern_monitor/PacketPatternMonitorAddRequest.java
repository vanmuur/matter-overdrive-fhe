/*
 * This file is part of MatterOverdrive: Legacy Edition
 * Copyright (C) 2019, Horizon Studio <contact@hrznstudio.com>, All rights reserved.
 *
 * MatterOverdrive: Legacy Edition is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MatterOverdrive: Legacy Edition is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Matter Overdrive.  If not, see <http://www.gnu.org/licenses>.
 */

package matteroverdrive.network.packet.server.pattern_monitor;

import io.netty.buffer.ByteBuf;
import matteroverdrive.api.network.MatterNetworkTaskState;
import matteroverdrive.data.matter_network.ItemPattern;
import matteroverdrive.machines.pattern_monitor.ComponentTaskProcessingPatternMonitor;
import matteroverdrive.machines.pattern_monitor.TileEntityMachinePatternMonitor;
import matteroverdrive.machines.replicator.TileEntityMachineReplicator;
import matteroverdrive.matter_network.tasks.MatterNetworkTaskReplicatePattern;
import matteroverdrive.network.packet.TileEntityUpdatePacket;
import matteroverdrive.network.packet.server.AbstractServerPacketHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketPatternMonitorAddRequest extends TileEntityUpdatePacket {
    private ItemPattern pattern;
    private int amount;
    private long destination;
    private String message;

    public PacketPatternMonitorAddRequest() {
        super();
    }

    public PacketPatternMonitorAddRequest(TileEntityMachinePatternMonitor monitor, ItemPattern pattern, int amount, BlockPos destination, String message) {
        this(monitor, pattern, amount);

        this.destination = destination.toLong();
        this.message = message;
    }

    public PacketPatternMonitorAddRequest(TileEntityMachinePatternMonitor monitor, ItemPattern pattern, int amount, BlockPos destination) {
        this(monitor, pattern, amount);

        this.destination = destination.toLong();
    }

    public PacketPatternMonitorAddRequest(TileEntityMachinePatternMonitor monitor, ItemPattern pattern, int amount) {
        super(monitor);
        this.pattern = pattern;
        this.amount = amount;

        this.destination = -1;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        pattern = ItemPattern.fromBuffer(buf);
        amount = buf.readShort();
        destination = buf.readLong();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        ItemPattern.writeToBuffer(buf, pattern);
        buf.writeShort(amount);
        buf.writeLong(destination);
    }

    public static class ServerHandler extends AbstractServerPacketHandler<PacketPatternMonitorAddRequest> {
        @Override
        public void handleServerMessage(EntityPlayerMP player, PacketPatternMonitorAddRequest message, MessageContext ctx) {
            TileEntity entity = message.getTileEntity(player.world);

            if (entity instanceof TileEntityMachinePatternMonitor) {
                TileEntityMachinePatternMonitor monitor = (TileEntityMachinePatternMonitor) entity;

                if (monitor != null) {
                    BlockPos pos = BlockPos.fromLong(message.destination);

                    MatterNetworkTaskReplicatePattern task;

                    System.out.println("Entity type: " + player.world.getTileEntity(pos).getClass().getName());

                    if (! (player.world.getTileEntity(pos) instanceof TileEntityMachineReplicator)) {
                        System.out.println("Machine is NOT a replicator.");
                        task = new MatterNetworkTaskReplicatePattern(message.pattern, message.amount, message.destination, "Invalid destination.");
                    } else {
                        System.out.println("Machine IS a replicator.");
                        task = new MatterNetworkTaskReplicatePattern(message.pattern, message.amount, message.destination);
                    }

                    task.setState(MatterNetworkTaskState.WAITING);
                    monitor.getComponent(ComponentTaskProcessingPatternMonitor.class).addReplicateTask(task);
                }
            }
        }
    }
}
