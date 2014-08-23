package dnevnik;

import java.util.HashMap;

public class sqlStavki {
		static HashMap<String,String> sql_stavki = new HashMap<String,String>();

	public sqlStavki(){
            /*
             * Sql stavki za učno pripravo                                             *
             */                                             
             sql_stavki.put("Seznam_Priprav","select p.id , pu.Naslov_Ure, pu.Datum from dnevnik.podatki_ure pu, dnevnik.ucna_priprava p WHERE pu.id = p.podatki_ure_id AND UPORABNIK_ID = ? ORDER BY pu.Datum DESC;");

             //select stavki
            sql_stavki.put("poisci_podatki_ure_priprava","SELECT PODATKI_URE_ID FROM dnevnik.ucna_priprava WHERE ID = ?;");
            sql_stavki.put("poisci_id_priprave", "SELECT ID FROM dnevnik.ucna_priprava WHERE PODATKI_URE_ID = ?");
            sql_stavki.put("poisci_vrstice_pregled_ure", "SELECT * FROM pregled_ure WHERE UCNA_PRIPRAVA_ID = ? ORDER BY ZAP");
            sql_stavki.put("poisci_vrstice_podrobni_pregled_ure", "SELECT * FROM podrobni_pregled WHERE UCNA_PRIPRAVA_ID = ? ORDER BY ZAP");
            sql_stavki.put("poisci_id_podatki_ure", "select id from dnevnik.podatki_ure a where datum = STR_TO_DATE(? ,'%m.%d.%Y') AND IZVAJALEC = ? AND MENTOR = ? AND URA = ?;");
            sql_stavki.put("select_cilji_ure","select cilj from dnevnik.cilji_ure WHERE PODATKI_URE_ID = ?;");
            sql_stavki.put("select_podatki_ure","select a.ID, a.NASLOV_URE, a.IZVAJALEC, a.MENTOR, a.DATUM, a.URA, a.RAZRED, a.SOLA, a.TEMA, a.SKLOP, a.ENOTA, a.PRISTOP, a.OBLIKE, a.METODE, a.PRIPOMOCKI, a.VIRI from dnevnik.podatki_ure a, dnevnik.ucna_priprava b WHERE b.PODATKI_URE_ID = a.ID AND b.ID = ?;");
            sql_stavki.put("select_dodatne_dej","select D_SIBKI_U, D_ZMOZNI_U FROM podatki_ure WHERE UCNA_PRIPRAVA_ID = ?");

            //Insert stavki
            sql_stavki.put("insert_priprava", "INSERT INTO dnevnik.ucna_priprava (UPORABNIK_ID, PODATKI_URE_ID ) VALUES (?, ?);");                                            
            sql_stavki.put("insert_zgradba_ure", "INSERT INTO dnevnik.pregled_ure (UCNA_PRIPRAVA_ID,CILJ,STRATEGIJA,NACIN,METODE,CAS,PRIPOMOCKI,ZAP) VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
            sql_stavki.put("insert_podatki_ure", "INSERT INTO  dnevnik.podatki_ure (NASLOV_URE, IZVAJALEC, MENTOR, DATUM, URA, RAZRED, SOLA, TEMA, SKLOP, ENOTA, PRISTOP, OBLIKE, METODE, PRIPOMOCKI, VIRI) VALUES (?, ?, ?, STR_TO_DATE(?,'%m.%d.%Y '), ?, ?, ?, ?, ? ,? ,? ,? ,? ,? ,?);");
            sql_stavki.put("insert_podrobni_pregled", "INSERT INTO dnevnik.podrobni_pregled (UCNA_PRIPRAVA_ID,NAMEN,DEJAVNOST_UCIT,DEJAVNOST_UCEN,TABELSKA_SLIKA,ZAP) VALUES (?, ?, ?, ?, ?, ?);");
            sql_stavki.put("insert_cilji_ure", "INSERT INTO dnevnik.cilji_ure (PODATKI_URE_ID,cilj) values(?, ?);");

            //Update stavki
            sql_stavki.put("update_podatki_ure", "UPDATE dnevnik.podatki_ure SET NASLOV_URE = ?, IZVAJALEC = ?, MENTOR = ?, DATUM = ?, URA = ?, RAZRED = ?, SOLA = ?, TEMA = ?, SKLOP = ?, ENOTA = ?, PRISTOP = ?, OBLIKE = ?, METODE = ?, PRIPOMOCKI = ?, VIRI = ? WHERE ID = ?;");
            sql_stavki.put("update_zgradba_ure", "UPDATE dnevnik.pregled_ure SET UCNA_PRIPRAVA_ID = ?, CILJ = ?, STRATEGIJA = ?,NACIN = ?, METODE = ?,CAS = ?,PRIPOMOCKI = ? WHERE ID = ?;"); 
            sql_stavki.put("update_podrobni_pregled", "UPDATE dnevnik.podrobni_pregled SET UCNA_PRIPRAVA_ID = ?, NAMEN = ?, DEJAVNOST_UCIT = ?,DEJAVNOST_UCEN = ?, TABELSKA_SLIKA = ? WHERE ID = ?;"); 
            sql_stavki.put("update_dodatne_dej","UPDATE podatki_ure SET D_SIBKI_U = ?, D_ZMOZNI_U = ? WHERE UCNA_PRIPRAVA_ID = ?");
            sql_stavki.put("update_podatki_ure_set_priprava","UPDATE podatki_ure SET UCNA_PRIPRAVA_ID = ? WHERE ID = ?");

            //Delete stavki
            sql_stavki.put("delete_zgradba_ure", "DELETE FROM dnevnik.pregled_ure WHERE id = ?;");
            sql_stavki.put("delete_podrobni_pregled", "DELETE FROM dnevnik.podrobni_pregled WHERE id = ?;");
            sql_stavki.put("delete_cilji_ure", "DELETE FROM dnevnik.cilji_ure WHERE PODATKI_URE_ID = ?;");

            /*
             * Sql stavki za hospitacije
             */                                            
            sql_stavki.put("Seznam_Hospitacij","select Naslov_Ure, Datum from dnevnik.hospitacije WHERE UPORABNIK_ID = ?;");
            sql_stavki.put("Seznam_Hospitacij_vse","select h.Naslov_Ure, h.Datum, concat(s.priimek, ' ', s.ime, ', ',s.id) as student from dnevnik.hospitacije h, dnevnik.studentje s Where h.uporabnik_id = s.id;");

            //Select stavki
            sql_stavki.put("poisci_id_hospitacije", "SELECT MAX(ID) AS ID FROM dnevnik.hospitacije WHERE UPORABNIK_ID = ? AND IZVAJALEC = ? AND DATUM = ? AND URA = ? AND RAZRED = ? AND TEMA = ? AND SKLOP = ? AND ENOTA = ? AND MENTOR = ? AND NASLOV_URE = ? AND SOLA = ?;");
            sql_stavki.put("select_hospitacija","select ID, IZVAJALEC, MENTOR, DATUM, URA, RAZRED, SOLA, TEMA, SKLOP, ENOTA, NASLOV_URE from dnevnik.hospitacije WHERE ID = ?;");
            sql_stavki.put("select_hosp_vidik_B","select id_hospitacije, opazovani_vidik, opazovani_vidik_opazanja, ravnanje_z_opazovanim_vidikom, razlaganje_opazanja, zakaj_je_vidkik_pomemben, utemeljitev_premislekov from dnevnik.hospitacija_vidik_b WHERE id_hospitacije = ?;");
            sql_stavki.put("select_hosp_vidik_A","select id_hospitacije, opis_ucne_ure, pristop_ucne_ure, vprasanje_uciteljici, mnenje_o_organizaciji_hospitacije from dnevnik.hospitacija_vidik_a WHERE id_hospitacije = ?;");

            //Insert stavki
            sql_stavki.put("insert_hospitacije", "INSERT INTO dnevnik.hospitacije (UPORABNIK_ID, IZVAJALEC, DATUM, URA, RAZRED, TEMA, SKLOP, ENOTA, MENTOR, NASLOV_URE, SOLA ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            sql_stavki.put("insert_hosp_vidik_a", "INSERT INTO dnevnik.hospitacija_vidik_a (id_hospitacije, opis_ucne_ure, pristop_ucne_ure, vprasanje_uciteljici, mnenje_o_organizaciji_hospitacije) VALUES (?, ?, ?, ?, ?);");
            sql_stavki.put("insert_hosp_vidik_b", "INSERT INTO dnevnik.hospitacija_vidik_b (id_hospitacije, opazovani_vidik, opazovani_vidik_opazanja, ravnanje_z_opazovanim_vidikom, razlaganje_opazanja, zakaj_je_vidkik_pomemben, utemeljitev_premislekov) VALUES (?, ?, ?, ?, ?, ?, ?);");

            //Update stavki                                            
            sql_stavki.put("update_hospitacije", "UPDATE dnevnik.hospitacije SET UPORABNIK_ID = ?, IZVAJALEC = ?, DATUM = ?, URA = ?, RAZRED = ?, TEMA = ?, SKLOP = ?, ENOTA = ?, MENTOR = ?, NASLOV_URE = ?, SOLA = ? WHERE ID = ?;");
            sql_stavki.put("update_hosp_vidik_a", "UPDATE dnevnik.hospitacija_vidik_a SET opis_ucne_ure = ?, pristop_ucne_ure = ?, vprasanje_uciteljici = ?, mnenje_o_organizaciji_hospitacije = ? WHERE id_hospitacije = ?;");
            sql_stavki.put("update_hosp_vidik_b", "UPDATE dnevnik.hospitacija_vidik_b SET opazovani_vidik = ?, opazovani_vidik_opazanja = ?, ravnanje_z_opazovanim_vidikom = ?, razlaganje_opazanja = ?, zakaj_je_vidkik_pomemben = ?, utemeljitev_premislekov = ? WHERE id_hospitacije = ?;");

             /*
              * Sql stavki za naloge
              */
            sql_stavki.put("Seznam_Nalog_dodaj","select n.id , n.ime, n.Datum_zaklj from dnevnik.naloge n WHERE n.aktivna = 0 AND N.UPORABNIK = ? ORDER BY n.Datum_zaklj DESC;");
            sql_stavki.put("Seznam_Nalog_oddaj","select n.id , n.ime, n.Datum_zaklj from dnevnik.naloge n WHERE n.aktivna = 1 AND n.predmet_id = ? AND n.Datum_zaklj >= CURDATE() ORDER BY n.Datum_zaklj DESC;");            
            sql_stavki.put("Seznam_Nalog_zakljucene","select n.id , n.ime, n.Datum_zaklj from dnevnik.naloge n WHERE n.aktivna = 2 AND n.predmet_id = ? AND n.Datum_zaklj < CURDATE() ORDER BY n.Datum_zaklj DESC;");            
            

            //Select stavki
            sql_stavki.put("poisci_podatke_nal","SELECT ime, datum_zaklj, opis FROM naloge WHERE id = ?;");
            sql_stavki.put("poisci_id_naloge","SELECT MAX(ID) AS ID FROM dnevnik.naloge WHERE IME = ? AND UPORABNIK = ?;");
            sql_stavki.put("poisci_nalogo", "SELECT * FROM naloge_elementi WHERE NALOGE_ID = ?;");
            sql_stavki.put("poisci_nalogo_oddaj", "SELECT * FROM naloge WHERE ID = ?;");
            sql_stavki.put("select_naloge_insert", "SELECT `INSERT` FROM NALOGE WHERE ID = ?;");
            sql_stavki.put("select_naloge_update", "SELECT `UPDATE` FROM NALOGE WHERE ID = ?;");
            sql_stavki.put("select_naloge_select", "SELECT `SELECT` FROM NALOGE WHERE ID = ?;");
            sql_stavki.put("select_el_naloge","select a.TIP, a.ID_KONTROLE, a.VREDNOST, a.id, a.zap FROM naloge_elementi a where a.NALOGE_ID = ? ORDER BY ZAP;");

            //Insert stavki
            sql_stavki.put("insert_naloga", "INSERT INTO dnevnik.naloge (IME, OPIS, DATUM_ZAKLJ,PREDMET_ID,UPORABNIK) VALUES(?, ?, ?, ?, ?);");		
            sql_stavki.put("insert_el_naloge", "INSERT INTO naloge_elementi (ID_KONTROLE, TIP, VREDNOST, NALOGE_ID, ZAP) VALUES(?, ?, ?, ?, ?);");

            //Update stavki             
            sql_stavki.put("update_naloga", "UPDATE dnevnik.naloge SET IME = ?, OPIS = ?, DATUM_ZAKLJ = ? WHERE ID = ?;");		
            sql_stavki.put("posodobi_naloge_insert", "UPDATE NALOGE SET `INSERT` = ? WHERE ID = ?");
            sql_stavki.put("posodobi_naloge_update", "UPDATE NALOGE SET `UPDATE` = ? WHERE ID = ?");
            sql_stavki.put("posodobi_naloge_select", "UPDATE NALOGE SET `SELECT` = ? WHERE ID = ?");
            sql_stavki.put("posodobi_el_naloge", "UPDATE naloge_elementi SET ID_KONTROLE  = ?, TIP = ?, VREDNOST = ? WHERE ID = ?");
            

            //Delete stavki
            sql_stavki.put("delete_el_naloge","DELETE FROM naloge_elementi WHERE ID = ?;");

            /*
             * Ostali SQL stavki
             */                                             
            sql_stavki.put("preveri_prijavo","select ifnull(id,'-1') as id, count(id) as ST, UPOR_SKUPINA_ID from uporabniki where UIME = UPPER(?) AND GESLO = UPPER(?);");
            sql_stavki.put("Seznam_Datotek","select * from dnevnik.v_datoteke WHERE UPORABNIK_ID = ?;");
	}   
		
	public static String getSqlString(String Ime) {
	    return sql_stavki.get(Ime);
	}
}