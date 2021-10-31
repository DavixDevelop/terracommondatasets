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

import static net.buildtheearth.terraplusplus.dep.net.daporkchop.lib.common.math.PMath.*;

/**
 * Represents a Koppen-Climate dataset
 * The returned values from 1 to 30 represent the following classes:
 * Af,Am,Aw,BWh,BWk,BSh,BSk,Csa,Csb,Csc,Cwa,Cwb,Cwc,Cfa,Cfb,Cfc,Dsa,Dsb,Dsc,Dsd,Dwa,Dwb,Dwc,Dwd,Dfa,Dfb,Dfc,Dfd,ET,EF
 *
 * @author DavixDevelop
 *
 */
public class KoppenClimate extends AbstractBuiltinDataset {
    protected static  final  int COLUMNS = 43200;
    protected static final int ROWS = 21600;

    public  KoppenClimate(){
        super(COLUMNS, ROWS);
    }

    private static final Ref<RLEByteArray> CACHE = Ref.soft((IOSupplier<RLEByteArray>) () -> {
        ByteBuf buffered = null;
        RLEByteArray.Builder builder = RLEByteArray.builder();
        try(LzmaInputStream is = new LzmaInputStream(KoppenClimate.class.getResourceAsStream("koppen_map.lzma"))){
            byte[] buffer = new byte[4096];
            int readyBytes;
            while((readyBytes = is.read(buffer, 0, 4096)) != -1){
                for(int i = 0; i < readyBytes; i++){
                    builder.append(buffer[i]);
                }
            }
        }

        /*for(int i = 0, s = buffered.readableBytes(); i < s; i++){
            builder.append(buffered.getByte(i));
        }*/

        return builder.build();
    });

    private  final  RLEByteArray data = CACHE.get();

    @Override
    protected double get(double xc, double yc) {
        int x = floorI(xc);
        int y = floorI(yc);

        if(x >= COLUMNS || x < 0 || y >= ROWS || y < 0)
            return 0;

        return  this.data.get(y * COLUMNS + x);
    }
}
