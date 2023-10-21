package core.ms.management.settings.impl;

import core.ms.management.settings.dao.entity.*;
import core.ms.management.settings.dao.repository.*;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class JewelImpl {

    @Inject
    JewelRepository jewelRepository;

    @Inject
    ClassificationRepository classificationRepository;

    @Inject
    MaterialRepository materialRepository;

    @Inject
    KaratRepository karatRepository;

    @Inject
    JewelCategoryRepository jewelCategoryRepository;


    public Response jewelListAll(){
        List<Jewel> jewelList = jewelRepository.jewelList();
        return Response.ok(jewelList).build();
    }

    public Response jewelSave(JsonObject jsonDataJewel){
        long idMaterial = Long.parseLong(jsonDataJewel.getString("idMaterial"));
        long idKarat = Long.parseLong(jsonDataJewel.getString("idKarat"));
        long idClassification = Long.parseLong(jsonDataJewel.getString("idClassification"));
        long idJewelCategory = Long.parseLong(jsonDataJewel.getString("idJewelCategory"));

        Material material = materialRepository.findById(idMaterial);
        Karat karat = karatRepository.findById(idKarat);
        Classification classification = classificationRepository.ClassificationFindById(idClassification);
        JewelCategory jewelCategory =jewelCategoryRepository.findById(idJewelCategory);

        Jewel jewel = new Jewel();
        jewel.setJewelCategory(jewelCategory);
        jewel.setClassification(classification);
        jewel.setKarat(karat);
        jewel.setMaterial(material);

        jewelRepository.jewelSave(jewel);
        return Response.ok().build();
    }
}
