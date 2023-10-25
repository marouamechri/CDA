package com.example.cda.services.impl;

import com.example.cda.modeles.Analysis;
import com.example.cda.repositorys.AnalysisRepository;
import com.example.cda.services.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalysisServiceImpl  implements AnalysisService {
    @Autowired
    AnalysisRepository analysisRepository;
    @Override
    public Analysis save(Analysis consultation) {
        return analysisRepository.save(consultation);
    }

    @Override
    public Analysis get(Long idAnalysis) {
        return analysisRepository.findById(idAnalysis).orElse(null);
    }

    @Override
    public void delete(Analysis analysis) {
        analysisRepository.delete(analysis);
    }
}
