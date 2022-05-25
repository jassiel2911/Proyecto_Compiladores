



import java.io.*;


public class bottomUpParser{
	static int contador=0;
	static 
	int flag=0;

	public static void main(String[] args) throws IOException, Exception{

		analizador();
	}

	//Definicion de la gramatica
	public static void regla1(String cadena){
		if(cadena.length()==2){
			System.out.println("T -> i");
			String cambio = cadena.replaceFirst("i","T");
			System.out.println("Cadena: "+cambio);
			regla5(cambio);
		}
		else if(cadena.length()>=3){
			System.out.println("T-> i");
			String cambio = cadena.replace("i+i","i+T");
			System.out.println("Cadena: "+cambio);
			if(cambio.equals(cadena)){
				System.out.println("\n******La cadena si fue aceptada******\n");	
			}
			else{
				switch(cambio){
				    case "i-i+T;" -> regla7(cambio);
				    case "i-i-T;" -> regla6(cambio);	
					case "E+T;" -> regla4(cambio);
					case "E-T;" -> regla3(cambio);
					case "i-(i+T);" -> regla7(cambio);
				    case "i-(i-T);" -> regla6(cambio);	
					case "(E+T);" -> regla4(cambio);
					case "(E-T);" -> regla3(cambio);
					default -> regla5(cambio);
				}
			}
		}
	}

	//Regla 2
	public static void regla2(String cadena){
		System.out.println("T -> E");
		String cambio = cadena.replace("(E)", "T");
		System.out.println("Cadena: "+cambio);
		regla6(cambio);
	}
	
	//Regla 3
	public static void regla3(String cadena){
		System.out.println("E -> E-T");
		String cambio = cadena.replace("E-T","E");
		System.out.println("Cadena: "+cambio);
		if(cambio.length()!=2){
			regla2(cambio);
		}else if(cambio.length()==2){
			regla11(cambio);
		}
	}

	//Regla4
	public static void regla4(String cadena){
		System.out.println("E -> E+T");
		String cambio = cadena.replace("E+T", "E");
		System.out.println("cadena: "+cambio);
		if(cambio.length()!=2){
			regla2(cambio);
		}
	}

	//Regla5
	public static void regla5(String cadena){
		if(cadena.length() == 2){
			System.out.println("E-> T");
			String cambio = cadena.replaceFirst("T", "E");
			System.out.println("Cadena: "+cambio);
		}
		else if(cadena.length() >= 3){
			contador++;
			System.out.println("E -> T");
			String cambio = cadena.replaceFirst("T","E");
			System.out.println("Cadena: "+cambio);
			if(cambio.contains("(") && flag != 0){
				regla2(cambio);
			}
			else{
				regla10(cambio);
			}
		}
	}

	//Regla6
	public static void regla6(String cadena){
		System.out.println("T-> i");
		String cambio = cadena.replace("i-T","T-T");
		System.out.println("cadena: "+cambio);
		if(cambio.length()!=2){
			regla8(cambio);
		}
	}


	//Regla7
	public static void regla7(String cadena){
		System.out.println("T-> i");
		String cambio = cadena.replace("i+T","T+T");
		System.out.println("cadena: "+cambio);
		if(cambio.length()!=2){
			regla9(cambio);
		}
	}


	//Regla8
	public static void regla8(String cadena){
		System.out.println("E-> T");
		String cambio = cadena.replace("T-T","E-T");
		System.out.println("cadena: "+cambio);
		if(cambio.length()!=2){
			regla3(cambio);
		}
	}

	//Regla9
	public static void regla9(String cadena){
		System.out.println("E-> T");
		String cambio = cadena.replace("T+T","E+T");
		System.out.println("cadena: "+cambio);
		if(cambio.length()!=2){
			regla4(cambio);
		}
	}

	//Regla10
	public static void regla10(String cadena){
		System.out.println("T-> i");
		String cambio = cadena.replace("i","T");
		System.out.println("cadena: "+cambio);
		if(cambio.length()!=2){
			regla8(cambio);
		}
	}

	//Regla11
	public static void regla11(String cadena){
		System.out.println("\n******La cadena si fue aceptada******\n");
	}



	public static void analizador() throws NoSuchFieldException, IOException{
		String cadena;
		int linea = 0, parentesis;
		FileReader prueba = new FileReader("./prueba.txt");
		BufferedReader b = new BufferedReader(prueba);
		while((cadena = b.readLine()) !=null){
			linea++;
			//esta linea de codigo delimita la expresion regular del ejemplos solicitado
			if(cadena.matches("i[+|-]?[(]*i[+|-]i[)]+[;]")){ //i-(i+i)
			//aqui mostrara la cadena solicitada
				System.out.println("\nEjemplo: "+cadena);
				if(cadena.contains("()")){ //buscara la presencia de parentesis
					parentesis = CP(cadena);
					if(parentesis != 0){
							System.out.print("Error en la linea: "+linea+". La cadena es: "+cadena+"\n");
					}
					else{
							flag = 0;
							regla1(cadena);
					}	
				}
				else{ 
					regla1(cadena);
				}
			}
			else{
				System.out.println("Error en la lina: "+linea+". La cadena es: "+cadena+"\n");
			}
		}
		b.close();
	}
		
	//Estado para los parentesis. Verifica que los parentesis
	//esten balanceados
	public static int CP(String cadena){
		int i = 0, f = 0;
		while(cadena.length()-1 != f){
			switch (cadena.charAt(f)){
				case '(' -> {
					i++;
					f++;	
				}
				case ')' -> {
					i--;
					f++;
				}
				default -> f++;
			}
		}
		return i;	
	}
}
