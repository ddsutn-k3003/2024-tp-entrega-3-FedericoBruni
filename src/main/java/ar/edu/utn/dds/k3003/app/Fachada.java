package ar.edu.utn.dds.k3003.app;

import ar.edu.utn.dds.k3003.facades.FachadaHeladeras;
import ar.edu.utn.dds.k3003.facades.FachadaViandas;
import ar.edu.utn.dds.k3003.facades.dtos.*;
import ar.edu.utn.dds.k3003.model.Heladera;
import ar.edu.utn.dds.k3003.model.Temperatura;
import ar.edu.utn.dds.k3003.repositories.HeladeraJPARepository;
import ar.edu.utn.dds.k3003.repositories.HeladeraMapper;
import ar.edu.utn.dds.k3003.repositories.HeladeraRepository;
import ar.edu.utn.dds.k3003.repositories.TemperaturaMapper;
import lombok.Getter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.util.*;

public class Fachada implements FachadaHeladeras {

    private HeladeraJPARepository heladeraRepository;

    @Getter
    private EntityManagerFactory entityManagerFactory;

    private HeladeraMapper heladeraMapper;
    private TemperaturaMapper temperaturaMapper;
    private FachadaViandas fachadaViandas;

    public Fachada() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("pruebadb");
        this.heladeraRepository = new HeladeraJPARepository(entityManagerFactory.createEntityManager());
        this.heladeraMapper = new HeladeraMapper();
        this.temperaturaMapper = new TemperaturaMapper();

    }

    public Fachada(HeladeraJPARepository heladeraRepository, HeladeraMapper heladeraMapper, TemperaturaMapper temperaturaMapper) {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("pruebadb");
        this.heladeraRepository = heladeraRepository;
        heladeraRepository.setEntityManager(entityManagerFactory.createEntityManager());
        this.heladeraMapper = heladeraMapper;
        this.temperaturaMapper = temperaturaMapper;
    }

    public Fachada(HeladeraJPARepository heladeraRepository, HeladeraMapper heladeraMapper, TemperaturaMapper temperaturaMapper, EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.heladeraRepository = heladeraRepository;
        heladeraRepository.setEntityManager(entityManagerFactory.createEntityManager());
        this.heladeraMapper = heladeraMapper;
        this.temperaturaMapper = temperaturaMapper;
    }


    @Override
    public HeladeraDTO agregar(HeladeraDTO heladeraDTO) {
        Heladera heladera = new Heladera(heladeraDTO.getNombre());

        this.heladeraRepository.getEntityManager().getTransaction().begin();
        heladera = this.heladeraRepository.save(heladera);
        this.heladeraRepository.getEntityManager().getTransaction().commit();

        return this.heladeraMapper.map(heladera);

    }

    @Override
    public void depositar(Integer heladeraId, String qrVianda) throws NoSuchElementException {

        this.heladeraRepository.getEntityManager().getTransaction().begin();
        Heladera heladera = this.heladeraRepository.findById(heladeraId);
        heladera.guardar(qrVianda);
        this.heladeraRepository.getEntityManager().getTransaction().commit();

        ViandaDTO viandaDTO = this.fachadaViandas.buscarXQR(qrVianda);
        this.fachadaViandas.modificarEstado(viandaDTO.getCodigoQR(), EstadoViandaEnum.DEPOSITADA);
    }

    @Override
    public Integer cantidadViandas(Integer heladeraId) throws NoSuchElementException {
        return this.heladeraRepository.findById(heladeraId).getCantidadViandas();
    }

    @Override
    public void retirar(RetiroDTO retiroDTO) throws NoSuchElementException {
        this.heladeraRepository.getEntityManager().getTransaction().begin();
        Heladera heladera = this.heladeraRepository.findById(retiroDTO.getHeladeraId());
        heladera.retirar(retiroDTO.getQrVianda());
        this.heladeraRepository.getEntityManager().getTransaction().commit();
        ViandaDTO viandaDTO = this.fachadaViandas.buscarXQR(retiroDTO.getQrVianda());
        this.fachadaViandas.modificarEstado(viandaDTO.getCodigoQR(), EstadoViandaEnum.RETIRADA);
    }

    @Override
    public void temperatura(TemperaturaDTO temperaturaDTO) {
        Temperatura temperatura = new Temperatura(temperaturaDTO.getTemperatura(), temperaturaDTO.getHeladeraId(), temperaturaDTO.getFechaMedicion());
        Heladera heladera = this.heladeraRepository.findById(temperaturaDTO.getHeladeraId());

        this.heladeraRepository.getEntityManager().getTransaction().begin();
        heladera = this.heladeraRepository.save(heladera);
        this.heladeraRepository.getEntityManager().getTransaction().commit();

        heladera.setTemperatura(temperatura);
    }

    @Override
    public List<TemperaturaDTO> obtenerTemperaturas(Integer heladeraId) {
        Heladera heladera = this.heladeraRepository.findById(heladeraId);
        List<TemperaturaDTO> temperaturasHeladera = new ArrayList<>();
        heladera.getTemperaturas().forEach(temperatura -> temperaturasHeladera.add(this.temperaturaMapper.map(temperatura)));
        return temperaturasHeladera;
    }
    @Override
    public void setViandasProxy(FachadaViandas fachadaViandas) {
        this.fachadaViandas = fachadaViandas;

    }

    public HeladeraDTO buscarXId(Integer heladeraId) throws NoSuchElementException {
        return this.heladeraMapper.map(this.heladeraRepository.findById(heladeraId));
    }
}
