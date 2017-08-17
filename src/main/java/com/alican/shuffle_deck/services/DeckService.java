package com.alican.shuffle_deck.services;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.alican.shuffle_deck.model.Deck;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Alican on 8/15/17.
 */
@Path("/player")
public class DeckService{
	
	// PUT an idempotent request for the creation of a new named deck. New decks
	// are created in some initial sorted order
	@PUT
	@Path("/deck/{deckName}")
	public Response putDeck(@PathParam("deckName") String deckName, @Context HttpServletRequest req) {
		// Saving data in-memory using servlet context
		ServletContext sc = req.getServletContext(); 
		String responseStr = "";
		String shuffleType = (String)sc.getAttribute("shuffleType");
		Deck deck = null;
		if (sc != null) {
			deck = new Deck();
			deck.setDeckName(deckName);
			deck.setShuffleType(shuffleType);
			sc.setAttribute("deck_" + deckName, deck);
			responseStr = toJson((Deck) sc.getAttribute("deck_" + deckName)) + "\n";
		}

		return Response.status(201).entity(responseStr).build();
	}
	
	// GET a named deck in its current sorted/shuffled order
	@GET
	@Path("/deck/{deckName}")
	public Response sayHello(@PathParam("deckName") String deckName, @Context HttpServletRequest req) {
		ServletContext sc = req.getServletContext();
		String responseStr;
		if (sc != null) {
			if (sc.getAttribute("deck_" + deckName) != null) {
				responseStr = toJson(sc.getAttribute("deck_" + deckName)) + "\n";
			} else {
				return Response.status(404).entity("Deck not found \n").build();
			}
		} else {
			return Response.status(404).entity("Context error").build();
		}
		return Response.status(200).entity(responseStr).build();
	}

	// POST a request to shuffle an existing named deck.
	@POST
	@Path("/shuffle/{deckName}")
	public Response shuffleDeck(@PathParam("deckName") String deckName, @Context HttpServletRequest req) {
		ServletContext sc = req.getServletContext();
		String responseStr;
		Deck deck = null;
		if (sc != null) {
			deck = (Deck) sc.getAttribute("deck_" + deckName);
			if (deck == null) {
				return Response.status(404).entity("Deck not found \n").build();
			}
			deck.shuffle();
			sc.setAttribute("deck_" + deckName, deck);
			responseStr = toJson((Deck) sc.getAttribute("deck_" + deckName));
		} else {
			return Response.status(404).entity("Context error").build();
		}
		return Response.status(200).entity(responseStr).build();
	}

	// GET a list of the current decks persisted in the service.
	@GET
	@Path("/alldecks")
	public Response getAllDecks(@Context HttpServletRequest req) {
		ServletContext sc = req.getServletContext();
		String responseStr = "";
		if (sc != null) {
			Enumeration<?> e = sc.getAttributeNames();
			while (e.hasMoreElements()) {
				String name = (String) e.nextElement();
				Object value = sc.getAttribute(name);
				if (value instanceof Deck) {
					responseStr += toJson((Deck) sc.getAttribute(name)) + "\n";
				}
			}
		} else {
			return Response.status(404).entity("Context error").build();
		}
		return Response.status(200).entity(responseStr).build();
	}

	// DELETE a named deck
	@DELETE
	@Path("/deck/{deckName}")
	public Response removeDeck(@PathParam("deckName") String deckName, @Context HttpServletRequest req) {
		ServletContext sc = req.getServletContext();
		String responseStr = "";
		if (sc != null) {
			sc.removeAttribute("deck_" + deckName);
			responseStr = deckName + " removed\n";
		} else {
			return Response.status(404).entity("Context error").build();
		}
		return Response.status(200).entity(responseStr).build();
	}
	
	public static String toJson(Object object) {
		Gson gson = new GsonBuilder().create();
        return gson.toJson(object);
    }
}
