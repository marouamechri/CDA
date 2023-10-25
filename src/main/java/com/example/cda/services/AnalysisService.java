package com.example.cda.services;

import com.example.cda.modeles.*;

import java.util.List;

public interface AnalysisService {
    Analysis save(Analysis consultation);
    Analysis get (Long idAnalysis );
    void delete (Analysis analysis);

}
