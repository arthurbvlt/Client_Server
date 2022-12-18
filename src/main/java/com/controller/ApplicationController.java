package com.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.model.Ville;
import com.service.VilleService;


@Controller
public class ApplicationController {
	
	VilleService v = new VilleService();

	@GetMapping("/home")
	public String home(Model model, HttpServletRequest request) throws UnirestException {
		List<Ville> villes = v.getVilles();	
		model.addAttribute("villes", villes);
		return "home";
	}
	
	@PostMapping("/home")
	public String getDistance(Model model, HttpServletRequest request) throws UnirestException {
		List<Ville> villes = v.getVilles();	
		int ville1Index = 0;
		int ville2Index = 0;
		model.addAttribute("villes", villes);
		for (int i = 0; i < villes.size(); i++) {
			if (villes.get(i).getNomCommune().equals(request.getParameter("ville1"))) {
				ville1Index = i;
			}
		}
		for (int i = 0; i < villes.size(); i++) {
			if (villes.get(i).getNomCommune().equals(request.getParameter("ville2"))) {
				ville2Index = i;
			}
		}
		model.addAttribute("distance", v.getDistance(villes.get(ville1Index), villes.get(ville2Index)));
		return "home";
	}
	
	@GetMapping("/listVilles")
	public String listVille(Model model) throws UnirestException {
		List<Ville> villes = v.getVilles();	
		model.addAttribute("villes", villes);
		return "listVilles";
	}
	
	@GetMapping("/listVilles/{codeCommune}")
	public String ville(Model model, HttpServletRequest request, @PathVariable String codeCommune) throws UnirestException {
		Ville ville = v.getVille(codeCommune);	
		model.addAttribute("ville", ville);
		return "ville";
	}
	
	@PostMapping("/listVilles/{codeCommune}")
	public String modifVille(Model model, HttpServletRequest request, @PathVariable String codeCommune) throws UnirestException, UnsupportedEncodingException {
	    String nomCommune = request.getParameter("nomCommune");
	    String codePostal = request.getParameter("codePostal");
	    String libelleAcheminement = request.getParameter("libelleAcheminement");
	    String ligne = request.getParameter("ligne");
	    String latitude = request.getParameter("latitude");
	    String longitude = request.getParameter("longitude");
	    
	    v.updateVille(codeCommune, nomCommune, codePostal, libelleAcheminement, ligne, latitude, longitude);
	    
		Ville ville = v.getVille(codeCommune);
		model.addAttribute("ville", ville);
		
		return "ville";
	}
	
}
