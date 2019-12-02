package com.acme.info;

import java.util.ArrayList;
import java.util.List;

public class Info {

    private List<String> clani;
    private String opis_projekta;

    // TODO: spremeni v endpoints
    private List<String> mikrostoritve;
    private List<String> github;
    private List<String> travis;
    private List<String> dockerhub;

    public Info(String opis) {
        this.opis_projekta = opis;

        this.clani = new ArrayList<String>();
        this.mikrostoritve = new ArrayList<String>();
        this.github = new ArrayList<String>();
        this.travis = new ArrayList<String>();
        this.dockerhub = new ArrayList<String>();
    }

    public List<String> getClani() { return clani; }

    public void setClani(List<String> clani) { this.clani = clani; }

    public String getOpis_projekta() { return opis_projekta; }

    public void setOpis_projekta(String opis_projekta) { this.opis_projekta = opis_projekta; }

    public List<String> getMikrostoritve() { return mikrostoritve; }

    public void setMikrostoritve(List<String> mikrostoritve) { this.mikrostoritve = mikrostoritve; }

    public List<String> getGithub() { return github; }

    public void setGithub(List<String> github) { this.github = github; }

    public List<String> getTravis() { return travis; }

    public void setTravis(List<String> travis) { this.travis = travis; }

    public List<String> getDockerhub() { return dockerhub; }

    public void setDockerhub(List<String> dockerhub) { this.dockerhub = dockerhub; }




    public void addClan(String clan) {
        this.clani.add(clan);
    }

    public void addMikrostoritev(String mikro) {
        this.mikrostoritve.add(mikro);
    }

    public void addGitHub(String hub) {
        this.github.add(hub);
    }

    public void addTravis(String travis) {
        this.travis.add(travis);
    }

    public void addDockerHub(String dockHub) {
        this.dockerhub.add(dockHub);
    }
}
