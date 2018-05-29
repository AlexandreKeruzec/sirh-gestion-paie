package dev.paie.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.paie.entite.Cotisation;
import dev.paie.entite.Entreprise;
import dev.paie.entite.Grade;
import dev.paie.entite.Periode;
import dev.paie.entite.ProfilRemuneration;

@Service
public class InitialiserDonneesServiceDev implements InitialiserDonneesService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private CotisationService cotisationService;
	@Autowired
	private PeriodeService periodeService;

	@Override
	@Transactional
	public void initialiser() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("cotisations-imposables.xml",
				"cotisations-non-imposables.xml", "entreprises.xml", "grades.xml", "profils-remuneration.xml");
		for (Periode periode : genererPeriodes()) {
			System.out.println(periode.getDateFin().toString());
			periodeService.sauvegarder(periode);
		}

		Map<String, Cotisation> cotList = context.getBeansOfType(Cotisation.class);
		for (Cotisation cot : cotList.values()) {
			cotisationService.sauvegarder(cot);
		}

		Map<String, Grade> gradeList = context.getBeansOfType(Grade.class);
		for (Grade grade : gradeList.values()) {
			em.persist(grade);
		}

		Map<String, Entreprise> entrepriseList = context.getBeansOfType(Entreprise.class);
		for (Entreprise entreprise : entrepriseList.values()) {
			em.persist(entreprise);
		}

		Map<String, ProfilRemuneration> profilList = context.getBeansOfType(ProfilRemuneration.class);
		for (ProfilRemuneration profil : profilList.values()) {
			em.persist(profil);
		}

	}

	private List<Periode> genererPeriodes() {
		List<Periode> periodeList = new ArrayList<>();

		for (int i = 1; i <= 12; i++) {
			Periode periode = new Periode();
			LocalDate start = LocalDate.of(2017, i, 1);
			LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
			periode.setDateDebut(start);
			periode.setDateFin(end);
			periodeList.add(periode);
		}

		return periodeList;
	}

}
