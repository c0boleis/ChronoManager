package fr.chrono.test;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import fr.chrono.controlers.CategoryControler;
import fr.chrono.controlers.CompetiteurControler;
import fr.chrono.model.interfaces.ICompetiteur;

public class CompetiteurControlerTest {

	@Ignore
	@Test
	public void testRegexCategory() {
		String[] texts = new String[] {"C1H","C1_H","fr.test",
				"c1H",null,"C1#u",
				"Corenrin,Brest","try;k","TEST",
				"C1_H/HR","FR","K\"R\""};
		boolean[] results = {true,true,false,
				true,false,false,
				false,false,true,
				false,true,false};
		for(int index = 0;index<texts.length;index++) {
			String result = CategoryControler.checkCategory(texts[index]);
			String expextedResult = texts[index];
			if(CategoryControler.CATEGORY_UPPER_CASE_ONLY && expextedResult != null) {
				expextedResult = expextedResult.toUpperCase();
				System.out.println("upperCase");
			}
			if(results[index]) {
				Assert.assertEquals(expextedResult, result);
			}else {
				Assert.assertNull(result);
			}

		}
	}

	@Ignore
	@Test
	public void testRegexName() {
		String[] texts = new String[] {"Corentin BOLEIS","Corentin Boleis///Pierre REBOUL","Pierr-Marie Ber",
				"Corentin_BOLEIS","Héléna","Test;Grep"};
		boolean[] results = {true,true,true,
				true,false,false};
		for(int index = 0;index<texts.length;index++) {
			String result = CompetiteurControler.checkName(texts[index]);
			String expextedResult = texts[index];
			if(results[index]) {
				Assert.assertEquals(expextedResult, result);
			}else {
				Assert.assertNull(result);
			}

		}
	}

	@Ignore
	@Test(timeout=250)
	public void testLoadStartList() throws IOException {
		CompetiteurControler.load(new File("resources/liste_depart.csv"));
		ICompetiteur[] competiteurs = CompetiteurControler.getCompetiteurs();
		String[] categories = CategoryControler.getCategories();
		Assert.assertEquals(24, competiteurs.length);
		Assert.assertEquals(6, categories.length);
	}

}
