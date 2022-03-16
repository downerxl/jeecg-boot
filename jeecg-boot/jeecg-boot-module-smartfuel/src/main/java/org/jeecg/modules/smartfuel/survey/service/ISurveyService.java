package org.jeecg.modules.smartfuel.survey.service;

public interface ISurveyService {

  Object getCoalresults();

  Object GetStockyardMaterials(int StockyardID, double LevelHeight, double X1, double X2);

  Object GetCurMat(int BucketWheelID);

  Object GetFieldHeights(int StockyardID, double OffsetX, String Dists);

  Object GetVersion();
}
