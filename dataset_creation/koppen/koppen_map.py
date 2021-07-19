from osgeo import ogr
from osgeo import gdal
from qgis.core import *
from datetime import datetime
import numpy as np
import lzma
import pickle

gdal.UseExceptions()

CATEGORY = "BiomeMap"

def processMap(task):

    time_start = datetime.now()

    try:

        koppen = "C:/Users/david/Documents/GitHub/terraimprovedbiomes/project_resources/Beck_KG_V1_present_0p0083.tif"
        out_file = "C:/Users/david/Documents/GitHub/terraimprovedbiomes/biomemap/koppen_map.lzma"

        ds = gdal.Open(koppen)

        koppen_data = ds.GetRasterBand(1).ReadAsArray()

        ds = None

        oned_koppen = koppen_data.flatten()

        lzc = lzma.LZMACompressor(format=lzma.FORMAT_ALONE)

        with open(out_file, 'wb') as cf:
            cf.write(lzc.compress(oned_koppen) + lzc.flush())

        #pickle.dump(oned_koppen, lzma.open(out_file, 'wb' ) )


        #np.savetxt(csv_file, koppen_data, delimiter=";")

        """
        p = -1
        for x in range(0, 43200):
            for y in range(0, 21600):
                p += 1
                task.setProgress(int(round((p * 100) / 933120000)))
                k = koppen_data[y][x]
                #QgsMessageLog.logMessage('{name}'.format(name=str(k)),CATEGORY, Qgis.Info)
                csv_file.write("{c};\n".format(c=k))

        csv_file.close()
        """

    except Exception as e:
        QgsMessageLog.logMessage(
                    'Erroe: {error}'.format(error=str(e)),
                    CATEGORY, Qgis.Info)

    return time_start

def mapCompleted(task, res=None):
    if res is not None:
        time_end = datetime.now()
        eclipsed = (time_end - res).total_seconds() / 60.0
        minutes = math.floor(eclipsed)
        seconds = math.floor((eclipsed - minutes) * 60)
        QgsMessageLog.logMessage('Done creating biome map in {minutes} minutes and {seconds} seconds'.format(minutes=minutes, seconds=seconds), CATEGORY, Qgis.Info)

task = QgsTask.fromFunction('Create biome map', processMap, on_finished=mapCompleted)
QgsApplication.taskManager().addTask(task)
