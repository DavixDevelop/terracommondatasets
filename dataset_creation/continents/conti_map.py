from osgeo import ogr
from qgis.core import *
from osgeo import gdal
from datetime import datetime
import numpy as np
import lzma
from time import sleep

CATEGORY = "ContiMap"

def processMap(task):
    time_start = datetime.now()

    try:

        #conti = QgsVectorLayer("C:/Users/david/Documents/GitHub/terracustomtreerepo/project_resources/continents/continents.shp","continents","ogr")

        conti_mapping = {"Africa" : 1, "Asia" : 2, "Europe" : 8, "Oceania" : 5, "South America" : 6, "Australia" : 3, "North America" : 4}

        out_file = "C:/Users/david/Documents/GitHub/terracustomtreerepo/treemap/continents_map.lzma"
        out_file2 = "C:/Users/david/Documents/GitHub/terracustomtreerepo/treemap/continents_map.png"


        conti_ds = ogr.Open("C:/Users/david/Documents/GitHub/terracustomtreerepo/project_resources/continents/continents.shp")

        pixel_size = 0.1

        source_layer = conti_ds.GetLayer()
        source_srs = source_layer.GetSpatialRef()
        x_min, x_max, y_min, y_max = source_layer.GetExtent()

        width = int((x_max - x_min) / pixel_size)
        height = int((y_max - y_min) / pixel_size)

        mem_driver = gdal.GetDriverByName("MEM")
        conti_ras = mem_driver.Create('', width, height, 1, gdal.GDT_Byte)
        conti_ras.SetGeoTransform((x_min, pixel_size, 0, y_max, 0, -pixel_size))
        band = conti_ras.GetRasterBand(1)
        band.SetNoDataValue(0)

        gdal.RasterizeLayer(conti_ras, [1], source_layer, options=['ATTRIBUTE=FID'])

        gdal.GetDriverByName('PNG').CreateCopy(out_file2, conti_ras)

        conti_map = conti_ras.GetRasterBand(1).ReadAsArray();

        conti_ras = None


        #map_data = np.empty((360, 720), dtype=np.uint8)
        #long = (((x + 0.5) * 360) / 43200) - 180
        #lat = (((y + 0.5) * -180) / 21600) + 90

        lzc = lzma.LZMACompressor(format=lzma.FORMAT_ALONE)
        oned_map = conti_map.flatten();

        with open(out_file, 'wb') as cf:
            cf.write(lzc.compress(oned_map) + lzc.flush())


    except Exception as e:
        QgsMessageLog.logMessage(
                    'Error: {error}'.format(error=str(e)),
                    CATEGORY, Qgis.Info)

    return time_start

def mapCompleted(exception, result=None):
    if result is not None:
        time_end = datetime.now()
        eclipsed = (time_end - result).total_seconds() / 60.0
        minutes = math.floor(eclipsed)
        seconds = math.floor((eclipsed - minutes) * 60)
        QgsMessageLog.logMessage(
            'Created continets map in {minutes} minutes and {seconds} seconds'.format(minutes=minutes, seconds=seconds),
            CATEGORY, Qgis.Info)

process_task = QgsTask.fromFunction('Create continents map', processMap, on_finished=mapCompleted)
QgsApplication.taskManager().addTask(process_task)
