package GameClient;

public class Tris {
	char[][] matrice;
	char giocatoreDiTurno;
	
	public Tris(){
		giocatoreDiTurno = 'x';
		matrice=new char[3][3];
		for(int r=0;r<3;r++)
			for(int c=0;c<3;c++)
				matrice[r][c]=' ';
	}
	
	boolean inserisci(int r, int c){
		if(matrice[r][c]==' ') {
			matrice[r][c]=giocatoreDiTurno;
			return true;
		}
		return false;
	}
	
	void stampa(){
		for(int r=0;r<3;r++){
			System.out.println(""+matrice[r][0]+'|'+matrice[r][1]+'|'+matrice[r][2]);
			if(r!=2) System.out.println("------");
		}
	}
	
	
	boolean vittoria(char x){
		int contaSimboli;
		//controllo in riga
		for(int r=0;r<3;r++){
			contaSimboli=0;
			for(int c=0;c<3;c++)
				if(matrice[r][c]==x)
					contaSimboli++;
			if(contaSimboli==3) return true;
		}
		//controllo in colonna
		for(int c=0;c<3;c++){
			contaSimboli=0;
			for(int r=0;r<3;r++)
				if(matrice[r][c]==x)
					contaSimboli++;
			if(contaSimboli==3) return true;
		}
		//controllo in diagonale
		contaSimboli=0;
		for(int r=0;r<3;r++)
			if(matrice[r][r]==x)
				contaSimboli++;
		if(contaSimboli==3) return true;
		
		contaSimboli=0;
		for(int r=0;r<3;r++)
			if(matrice[2-r][r]==x)
				contaSimboli++;
		if(contaSimboli==3) return true;

		return false;
	}
	
	boolean pareggio(){
		int contaCasellePiene=0;
		for(int r=0;r<3;r++)
			for(int c=0;c<3;c++)
				if(matrice[r][c]!=' ')
					contaCasellePiene++;
		
		if(contaCasellePiene==9) return true;
		return false;
	}
	
	
	void cambioGiocatore() {
		if(giocatoreDiTurno=='x')
			giocatoreDiTurno='o';
		else giocatoreDiTurno='x';
	}
	
	boolean partitaFinita() {
		return vittoria('x') || vittoria('o') || pareggio();
	}
}
