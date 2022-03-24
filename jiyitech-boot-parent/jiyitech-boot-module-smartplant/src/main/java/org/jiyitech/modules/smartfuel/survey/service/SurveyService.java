package org.jiyitech.modules.smartfuel.survey.service;

public interface SurveyService {
    Object getCoalresults();

    Object GetStockyardMaterials(int StockyardID, double LevelHeight, double X1, double X2);

    Object GetCurMat(int BucketWheelID);

    Object GetFieldHeights(int StockyardID, double OffsetX, String Dists);

    Object GetVersion();
}
