package dnevnik;

import java.util.HashMap;

public class sqlStavki {
		static HashMap<String,String> sql_stavki = new HashMap<String,String>();

	public sqlStavki(){
		sql_stavki.put("ucna_tema_combobox", "select ID,TEMA from tema;");
		sql_stavki.put("ucni_sklop_combobox", "select ID,SKLOP from sklop WHERE TEMA_ID = ?;");
		sql_stavki.put("ucna_enota_combobox", "select ID,ENOTA from enota  WHERE SKLOP_ID = ?;");
		sql_stavki.put("insert_priprava", "INSERT INTO dnevnik.ucna_priprava (UPORABNIK_ID, PODATKI_URE_ID, PRAKSA_ID ) VALUES (?, ?, ?);");
		sql_stavki.put("insert_hospitacije", "INSERT INTO dnevnik.hospitacije (UPORABNIK_ID, IZVAJALEC, DATUM, URA, RAZRED, TEMA_ID, SKLOP_ID, ENOTA_ID, MENTOR, NASLOV_URE, SOLA ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
		sql_stavki.put("insert_hosp_vidik_a", "INSERT INTO dnevnik.hospitacija_vidik_a (id_hospitacije, opis_ucne_ure, pristop_ucne_ure, vprasanje_uciteljici, mnenje_o_organizaciji_hospitacije) VALUES (?, ?, ?, ?, ?);");
		sql_stavki.put("insert_hosp_vidik_b", "INSERT INTO dnevnik.hospitacija_vidik_b (id_hospitacije, opazovani_vidik, opazovani_vidik_opazanja, ravnanje_z_opazovanim_vidikom, razlaganje_opazanja, zakaj_je_vidkik_pomemben, utemeljitev_premislekov) VALUES (?, ?, ?, ?, ?, ?, ?);");
		sql_stavki.put("poisci_podatki_ure_priprava","SELECT PODATKI_URE_ID FROM dnevnik.ucna_priprava WHERE ID = ?;");
		sql_stavki.put("poisci_id_priprave", "SELECT ID FROM denvik.ucna_priprava WHERE PODATKI_URE_ID = ?");
		sql_stavki.put("poisci_id_hospitacije", "SELECT MAX(ID) AS ID FROM dnevnik.hospitacije WHERE UPORABNIK_ID = ? AND IZVAJALEC = ? AND DATUM = ? AND URA = ? AND RAZRED = ? AND TEMA_ID = ? AND SKLOP_ID = ? AND ENOTA_ID = ? AND MENTOR = ? AND NASLOV_URE = ? AND SOLA = ?;");
		sql_stavki.put("insert_podatki_ure", "INSERT INTO  dnevnik.podatki_ure (IZVAJALEC, MENTOR, DATUM, URA, RAZRED, SOLA, TEMA_ID, SKLOP_ID, ENOTA_ID, PRISTOP, OBLIKE, METODE, PRIPOMOCKI, VIRI) VALUES (?, ?, STR_TO_DATE(?,'%M %d %Y'), ?, ?, ?, ?, ? ,? ,? ,? ,? ,? ,?);");
		sql_stavki.put("update_podatki_ure", "UPDATE dnevnik.podatki_ure SET IZVAJALEC = ?, MENTOR = ?, DATUM = ?, URA = ?, RAZRED = ?, SOLA = ?, TEMA_ID = ?, SKLOP_ID = ?, ENOTA_ID = ?, PRISTOP = ?, OBLIKE = ?, METODE = ?, PRIPOMOCKI = ?, VIRI = ? WHERE ID = ?;");
		sql_stavki.put("update_hospitacije", "UPDATE dnevnik.hospitacije SET UPORABNIK_ID = ?, IZVAJALEC = ?, DATUM = ?, URA = ?, RAZRED = ?, TEMA_ID = ?, SKLOP_ID = ?, ENOTA_ID = ?, MENTOR = ?, NASLOV_URE = ?, SOLA = ? WHERE ID = ?;");
		sql_stavki.put("update_hosp_vidik_a", "UPDATE dnevnik.hospitacija_vidik_a SET opis_ucne_ure = ?, pristop_ucne_ure = ?, vprasanje_uciteljici = ?, mnenje_o_organizaciji_hospitacije = ? WHERE id_hospitacije = ?;");
		sql_stavki.put("update_hosp_vidik_b", "UPDATE dnevnik.hospitacija_vidik_b SET opazovani_vidik = ?, opazovani_vidik_opazanja = ?, ravnanje_z_opazovanim_vidikom = ?, razlaganje_opazanja = ?, zakaj_je_vidkik_pomemben = ?, utemeljitev_premislekov = ? WHERE id_hospitacije = ?;");
		sql_stavki.put("insert_zgradba_ure", "INSERT INTO dnevnik.pregled_ure (UCNA_PRIPRAVA_ID,CILJ,STRATEGIJA,NACIN,METODE,CAS,PRIPOMOCKI) VALUES (?, ?, ?, ?, ?, ?, ?);");
		sql_stavki.put("delete_zgradba_ure", "DELETE FROM dnevki.pregled_ure WHERE UCNA_PRIPRAVA_ID = ?;");
		sql_stavki.put("delete_dodatne_dej", "DELETE FROM dnevki.dodatne_dejavnosti WHERE UCNA_PRIPRAVA_ID = ?;");
		sql_stavki.put("insert_dodatne_dej", "INSERT INTO dnevnik.dodatne_dejavnosti (UCNA_PRIPRAVA_ID,ZA,DEJAVNOST) VALUES (?, ?, ?);");
		sql_stavki.put("poisci_id_podatki_ure", "select id from dnevnik.podatki_ure where datum = STR_TO_DATE(? ,'%M %d %Y') AND IZVAJALEC = ? AND MENTOR = ? AND URA = ?;");
		sql_stavki.put("insert_cilji_ure", "INSERT INTO dnevnik.cilji_ure (PODATKI_URE_ID,cilj) values(?, ?);");
		sql_stavki.put("delete_cilji_ure", "DELETE FROM dnevnik.cilji_ure WHERE PODATKI_URE_ID = ?;");
		sql_stavki.put("preveri_prijavo","select ifnull(id,'-1') as id, count(id) as ST, UPOR_SKUPINA_ID from uporabniki where UIME = UPPER(?) AND GESLO = UPPER(?);");
		sql_stavki.put("Seznam_Datotek","select * from dnevnik.v_datoteke WHERE UPORABNIK_ID = ?;");
		sql_stavki.put("Seznam_Hospitacij","select Naslov_Ure, Datum from dnevnik.hospitacije WHERE UPORABNIK_ID = ?;");
		sql_stavki.put("Seznam_Hospitacij_vse","select h.Naslov_Ure, h.Datum, concat(s.priimek, ' ', s.ime, ', ',s.id) as student from dnevnik.hospitacije h, dnevnik.studentje s Where h.uporabnik_id = s.id;");
		sql_stavki.put("Seznam_Priprav","select pu.id , pu.Naslov_Ure, pu.Datum from dnevnik.podatki_ure pu, dnevnik.ucna_priprava p WHERE pu.id = p.podatki_ure_id AND UPORABNIK_ID = ? ORDER BY pu.Datum DESC;");
		sql_stavki.put("select_podatki_ure","select ID, IZVAJALEC, MENTOR, DATUM, URA, RAZRED, SOLA, TEMA_ID, SKLOP_ID, ENOTA_ID, PRISTOP, OBLIKE, METODE, PRIPOMOCKI, VIRI from dnevnik.podatki_ure WHERE ID = ?;");
		sql_stavki.put("select_hospitacija","select ID, IZVAJALEC, MENTOR, DATUM, URA, RAZRED, SOLA, TEMA_ID, SKLOP_ID, ENOTA_ID, NASLOV_URE from dnevnik.hospitacije WHERE ID = ?;");
		sql_stavki.put("select_hosp_vidik_B","select id_hospitacije, opazovani_vidik, opazovani_vidik_opazanja, ravnanje_z_opazovanim_vidikom, razlaganje_opazanja, zakaj_je_vidkik_pomemben, utemeljitev_premislekov from dnevnik.hospitacija_vidik_b WHERE id_hospitacije = ?;");
		sql_stavki.put("select_hosp_vidik_A","select id_hospitacije, opis_ucne_ure, pristop_ucne_ure, vprasanje_uciteljici, mnenje_o_organizaciji_hospitacije from dnevnik.hospitacija_vidik_a WHERE id_hospitacije = ?;");
		sql_stavki.put("select_cilji_ure","select PODATKI_URE_ID,cilj from dnevnik.cilji_ure WHERE PODATKI_URE_ID = ?;");
		sql_stavki.put("insert_naloge", "INSERT INTO dnevnik.naloge (IME, OPIS, DATUM_ZAKLJ,PREDMET_ID,UPORABNIK) VALUES(?, ?, ?, ?, ?);");
		sql_stavki.put("poisci_id_naloge","SELECT MAX(ID) AS ID FROM dnevnik.naloge WHERE IME = ? AND UPORABNIK = ?;");
		sql_stavki.put("insert_naloge_elemetni", "INSERT INTO dnevnik.naloge_elementi (ID_KONTROLE, TIP, VREDNOST, NALOGE_ID) VALUES(?, ?, ?, ?);");
		sql_stavki.put("posodobi_naloge_insert", "UPDATE NALOGE SET `INSERT` = ? WHERE ID = ?");
		sql_stavki.put("posodobi_naloge_update", "UPDATE NALOGE SET `UPDATE` = ? WHERE ID = ?");
		sql_stavki.put("posodobi_naloge_select", "UPDATE NALOGE SET `SELECT` = ? WHERE ID = ?");
		sql_stavki.put("select_naloge_insert", "SELECT `INSERT` FROM NALOGE WHERE ID = ?;");
		sql_stavki.put("select_naloge_update", "SELECT `UPDATE` FROM NALOGE WHERE ID = ?;");
		sql_stavki.put("select_naloge_select", "SELECT `SELECT` FROM NALOGE WHERE ID = ?;");
		sql_stavki.put("Seznam_Nalog","SELECT N.IME, N.DATUM_ZAKLJ AS DATUM , N.OPIS, N.ID AS UREDI FROM NALOGE N, studentje_predmet SP WHERE N.PREDMET_ID = SP.PREDMET_ID AND SP.STUDENTJE_ID = ?;");
		sql_stavki.put("Seznam_Nalog_prof","SELECT N.IME, N.DATUM_ZAKLJ AS DATUM , N.OPIS, N.ID AS UREDI FROM NALOGE N, studentje_predmet SP WHERE N.PREDMET_ID = SP.PREDMET_ID AND N.UPORABNIK = ?;");
		sql_stavki.put("poisci_nalogo", "SELECT * FROM naloge_elementi WHERE NALOGE_ID = ?;");
		sql_stavki.put("poisci_nalogo_oddaj", "SELECT * FROM naloge WHERE ID = ?;");
	}
		
	public static String getSqlString(String Ime) {
	    return sql_stavki.get(Ime);
	}
}