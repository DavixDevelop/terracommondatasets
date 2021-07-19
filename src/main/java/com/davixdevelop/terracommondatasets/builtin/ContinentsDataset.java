package com.davixdevelop.terracommondatasets.builtin;

import LZMA.LzmaInputStream;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.buildtheearth.terraplusplus.dataset.builtin.AbstractBuiltinDataset;
import net.buildtheearth.terraplusplus.util.RLEByteArray;
import net.buildtheearth.terraplusplus.dep.net.daporkchop.lib.binary.oio.StreamUtil;
import net.buildtheearth.terraplusplus.dep.net.daporkchop.lib.common.function.io.IOSupplier;
import net.buildtheearth.terraplusplus.dep.net.daporkchop.lib.common.ref.Ref;

import java.io.InputStream;

import static net.buildtheearth.terraplusplus.dep.net.daporkchop.lib.common.math.PMath.floorI;

/**
 * Represents a Continents dataset
 * The returned values (double) can be represented with the following Dictionary:
 * {"Africa" : 1, "Asia" : 2, "Europe" : 8, "Oceania" : 5, "South America" : 6, "Australia" : 3, "North America" : 4}
 *
 * @author DavixDevelop
 *
 */
public class ContinentsDataset extends AbstractBuiltinDataset {
    protected static  final  int COLUMNS = 3600;
    protected static final int ROWS = 1800;

    public ContinentsDataset(){
        super(COLUMNS, ROWS);
    }

    private static final Ref<RLEByteArray> CACHE = Ref.soft((IOSupplier<RLEByteArray>) () -> {
        ByteBuf buffered;
        try(InputStream is = new LzmaInputStream(ContinentsDataset.class.getResourceAsStream("continents_map.lzma"))){
            buffered = Unpooled.wrappedBuffer(StreamUtil.toByteArray(is));
        }

        RLEByteArray.Builder builder = RLEByteArray.builder();
        for(int i = 0, s = buffered.readableBytes(); i < s; i++){
            byte b = buffered.getByte(i);
            builder.append(b);
        }

        return builder.build();
    });

    private final RLEByteArray data = CACHE.get();

    @Override
    protected double get(double xc, double yc) {
        int x = floorI(xc);
        int y = floorI(yc);

        if(x >= COLUMNS || x < 0 || y >= ROWS || y < 0)
            return 0;

        return this.data.get(y * COLUMNS + x);
    }
}