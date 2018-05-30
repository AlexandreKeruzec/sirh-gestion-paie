package dev.paie.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dev.paie.entite.Cotisation;
import dev.paie.repository.CotisationRepository;

@RestController
public class CotisationController {

	@Autowired
	CotisationRepository cor;

	@RequestMapping(value = "/api/cotisations", method = RequestMethod.GET)
	public List<Cotisation> getAllCotisation() {
		return cor.findAll();
	}

	@RequestMapping(value = "/api/cotisations/{code}", method = RequestMethod.GET)
	// @ResponseBody // plus besoin de mettre cette annotation
	public ResponseEntity<Cotisation> findClient(@PathVariable String code) {
		Cotisation cot = cor.findByCode(code);
		if (cot != null) {
			return ResponseEntity.ok(cot);
		}
		return ResponseEntity.notFound().build();

	}

	@RequestMapping(value = "/api/cotisations", method = RequestMethod.POST)
	public void insertCotisation(@RequestBody Cotisation cotisation) {
		cor.save(cotisation);
	}

	@RequestMapping(value = "/api/cotisations/{code}", method = RequestMethod.PUT)
	public void updateCotisation(@PathVariable String code, @RequestBody Cotisation cotisation) {
		cotisation.setId(cor.findByCode(code).getId());
		cor.save(cotisation);
	}

	@RequestMapping(value = "/api/cotisations/{code}", method = RequestMethod.DELETE)
	public void deleteCotisation(@PathVariable String code) {
		try {
			cor.delete(cor.findByCode(code));
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
		}
	}

}
