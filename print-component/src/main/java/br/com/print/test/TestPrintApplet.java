package br.com.print.test;

import org.junit.BeforeClass;
import org.junit.Test;

import br.com.print.applet.PrintApplet;

public class TestPrintApplet {
	
	private static String impressoara;
	
	@BeforeClass
	public static void init(){
		System.out.println("init");	
		
		//impressoara = "MP-4000 TH";
		
		impressoara = "PDFCreator";
	}

	@Test
	public void impresssaoNormal() {
		PrintApplet printApplet = new PrintApplet();

		try {
			printApplet.print(null, 
					"17/01/2014 11:10",
					"HOSPITAL MODELO DO MV 4",
					null,
					"FILA PASSA PELA CLASSIFICACAO", 
					"SENHA: PAC0002",
					null, 
					impressoara, 
					impressoara,
					"2", 
					"CLASSIFICACAO", 
					"IMPRESSAO_NORMAL");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void impresssaoFormatada() {
		PrintApplet printApplet = new PrintApplet();

		try {
			printApplet.print("123456", 
					"17/01/2014 11:10",
					"HOSPITAL MODELO DO MV 4",
					"Especialidade: ANALISES CLINICAS",
					"FILA PASSA PELA CLASSIFICACAO", 
					"SENHA: PAC0002",
					"Prioridade: MUITO URGENTE", 
					impressoara, 
					impressoara,
					"2", 
					"CLASSIFICACAO", 
					"IMPRESSAO_FORMATADA");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void impresssaoFormatadaSemPrioridade() {
		PrintApplet printApplet = new PrintApplet();

		try {
			printApplet.print("123456", 
					"17/01/2014 11:10",
					"HOSPITAL MODELO DO MV 4",
					"Especialidade: ANALISES CLINICAS",
					"FILA PASSAPELA CLASSIFICACAO", 
					"SENHA: PAC0002",
					null, 
					impressoara, 
					impressoara,
					"2", 
					"CLASSIFICACAO", 
					"IMPRESSAO_FORMATADA");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void impresssaoFormatadaSemEspecialidade() {
		PrintApplet printApplet = new PrintApplet();

		try {
			printApplet.print("123456", 
					"17/01/2014 11:10",
					"HOSPITAL MODELO DO MV 4",
					null,
					"FILA PASSA PELA CLASSIFICACAO", 
					"SENHA: PAC0002",
					"Prioridade: CLASSIFICACAO", 
					impressoara, 
					impressoara,
					"2", 
					"CLASSIFICACAO", 
					"IMPRESSAO_FORMATADA");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void impresssaoFormatadaSemEspecialidadePrioridade() {
		PrintApplet printApplet = new PrintApplet();

		try {
			printApplet.print("123456", 
					"17/01/2014 11:10",
					"HOSPITAL MODELO DO MV 4",
					null,
					"FILA PASSA PELA CLASSIFICACAO", 
					"SENHA: PAC0002",
					null, 
					impressoara, 
					impressoara,
					"2", 
					"CLASSIFICACAO", 
					"IMPRESSAO_FORMATADA");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void impresssaoTotem() {
		PrintApplet printApplet = new PrintApplet();

		try {
			printApplet.print(null, 
					"17/01/2014 11:10",
					"HOSPITAL MODELO DO MV 4", 
					null,
					"FILA PASSA PELA CLASSIFICACAO", 
					"SENHA: PAC0002", 
					null,
					impressoara, 
					impressoara, 
					"1", 
					"CLASSIFICACAO",
					"IMPRESSAO_FORMATADA");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void getClientPrinters() {
		PrintApplet printApplet = new PrintApplet();

		String prints = printApplet.getClientPrinters();	
		
		String aa[] = prints.split("@P@");
		
		for(int i = 0; aa.length > i; i++){
			System.out.println(aa[i]);
		}			
	}

}
