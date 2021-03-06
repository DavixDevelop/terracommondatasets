package com.davixdevelop.terracommondatasets.builtin;

import LZMA.LzmaInputStream;
import io.netty.buffer.ByteBuf;
import net.buildtheearth.terraplusplus.dataset.builtin.AbstractBuiltinDataset;
import net.buildtheearth.terraplusplus.dep.net.daporkchop.lib.common.function.io.IOSupplier;
import net.buildtheearth.terraplusplus.dep.net.daporkchop.lib.common.reference.cache.Cached;
import net.buildtheearth.terraplusplus.util.RLEByteArray;

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

    private static final Cached<RLEByteArray> CACHE = Cached.global((IOSupplier<RLEByteArray>) () -> {
        ByteBuf buffered;
        RLEByteArray.Builder builder = RLEByteArray.builder();
        try(LzmaInputStream is = new LzmaInputStream(ContinentsDataset.class.getResourceAsStream("continents_map.lzma"))){
            byte[] buffer = new byte[4096];
            int readBytes;
            while((readBytes = is.read(buffer, 0, 4096)) != -1){
                for(int i = 0; i < readBytes; i++){
                    builder.append(buffer[i]);
                }
            }

            //buffered = Unpooled.wrappedBuffer(StreamUtil.toByteArray(is));
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