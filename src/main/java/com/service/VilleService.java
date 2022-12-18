package com.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.model.Coordonnee;
import com.model.Ville;
	
public class VilleService {
	
	public List<Ville> getVilles() throws UnirestException {
		
		 String url = "http://localhost:8181/ville";
		 HttpResponse <JsonNode> response = Unirest.get(url).asJson();
		 JSONArray villesJsonArray = response.getBody().getArray();
		 ArrayList<Ville> villes = new ArrayList<>();
		 for (int i = 0; i < villesJsonArray.length(); i++) {
			 Ville ville = new Ville();
			 JSONObject villeJsonObject = (JSONObject) villesJsonArray.get(i);
			 ville.setCodeCommune(villeJsonObject.getString("codeCommune"));
			 ville.setCodePostal(villeJsonObject.getString("codePostal"));
			 JSONObject coordonne = villeJsonObject.getJSONObject("coordonnee");
			 ville.setCoordonnee(new Coordonnee(coordonne.getString("latitude"), coordonne.getString("longitude")));
			 ville.setLibelleAcheminement(villeJsonObject.getString("libelleAcheminement"));
			 ville.setLigne(villeJsonObject.getString("ligne"));
			 ville.setNomCommune(villeJsonObject.getString("nomCommune"));
			 villes.add(ville);
		 }
		 
		 return villes; 
	}
	
	public Ville getVille(String codeCommune) throws UnirestException {
		
		String url = "http://localhost:8181/ville?codeCommune=" + codeCommune;
		HttpResponse <JsonNode> response = Unirest.get(url).asJson();
		 JSONArray villesJsonArray = response.getBody().getArray();
		 Ville ville = new Ville();
		 JSONObject villeJsonObject = (JSONObject) villesJsonArray.get(0);
		 ville.setCodeCommune(villeJsonObject.getString("codeCommune"));
		 ville.setCodePostal(villeJsonObject.getString("codePostal"));
		 JSONObject coordonne = villeJsonObject.getJSONObject("coordonnee");
		 ville.setCoordonnee(new Coordonnee(coordonne.getString("latitude"), coordonne.getString("longitude")));
		 ville.setLibelleAcheminement(villeJsonObject.getString("libelleAcheminement"));
		 ville.setLigne(villeJsonObject.getString("ligne"));
		 ville.setNomCommune(villeJsonObject.getString("nomCommune"));
		 
		 return ville; 
	}
	
	public double getDistance(Ville ville1, Ville ville2) {
		double latitudeV1 = Double.parseDouble(ville1.getCoordonnee().getLatitude())*Math.PI/180;
		double longitudeV1 = Double.parseDouble(ville1.getCoordonnee().getLongitude())*Math.PI/180;
		double latitudeV2 = Double.parseDouble(ville2.getCoordonnee().getLatitude())*Math.PI/180;
		double longitudeV2 = Double.parseDouble(ville2.getCoordonnee().getLongitude())*Math.PI/180;
		double dLong = longitudeV2 - longitudeV1;
		return Math.acos(Math.sin(latitudeV1)*Math.sin(latitudeV2) + Math.cos(latitudeV1)*Math.cos(latitudeV2)*Math.cos(dLong))*6371;
	}
	
	public void updateVille(String codeCommune, String nomCommune, String codePostal, String libelleAcheminement, String ligne, String latitude, String longitude) throws UnirestException, UnsupportedEncodingException {
		String url = "http://localhost:8181/ville/putVille?"
		+ "codeCommune=" + URLEncoder.encode(codeCommune, "UTF-8") 
		+ "&nomCommune=" + URLEncoder.encode(nomCommune, "UTF-8") 
		+ "&ligne=" + URLEncoder.encode(ligne, "UTF-8")
		+ "&libelleAcheminement=" + URLEncoder.encode(libelleAcheminement, "UTF-8")
		+ "&codePostal=" + URLEncoder.encode(codePostal, "UTF-8")
		+ "&latitude=" + URLEncoder.encode(latitude, "UTF-8")
		+ "&longitude=" + URLEncoder.encode(longitude, "UTF-8");
		@SuppressWarnings("unused")
		HttpResponse<String> response = Unirest.put(url).asString();
	}
}
