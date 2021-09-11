from osgeo import ogr
from osgeo import gdal
from qgis.core import *
from datetime import datetime
import numpy as np
import lzma
import pickle
import math

gdal.UseExceptions()

CATEGORY = "BiomeMap"

class Filter:
    def __init__(self, replace, replaceVal, minX, minY, maxX, maxY):
        self.replace = replace
        self.replaceVal = replaceVal
        self.minX = minX
        self.minY = minY
        self.maxX = maxX
        self.maxY = maxY


koppen_width = 43200
koppen_height = 21600

filter_list = [
    Filter(7, 9, 14.39590, 50.03900, 14.47242, 50.11610)
]

def processMap(task):

    time_start = datetime.now()

    try:

        koppen = "C:/Users/david/Documents/GitHub/terracommondatasets/project_resources/Beck_KG_V1_present_0p0083.tif"
        out_file = "C:/Users/david/Documents/GitHub/terracommondatasets/dataset_creation/koppen/koppen_map.lzma"

        ds = gdal.Open(koppen)

        koppen_data = ds.GetRasterBand(1).ReadAsArray()

        ds = None

        for filter in filter_list:
            minX = math.ceil(((filter.minX + 180) * koppen_width) / 360)
            minY = math.ceil((abs(filter.minY - 90) * koppen_height) / 180)
            maxX = math.floor(((filter.maxX + 180) * koppen_width) / 360)
            maxY = math.floor((abs(filter.maxY - 90) * koppen_height) / 180)
            for x in range(minX, maxX + 1):
                for y in range(maxY, minY + 1):
                    #QgsMessageLog.logMessage('{val}'.format(val=koppen_data[y][x]), CATEGORY, Qgis.Info)
                    if koppen_data[y][x] == filter.replace:
                        koppen_data[y][x] = filter.replaceVal

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
