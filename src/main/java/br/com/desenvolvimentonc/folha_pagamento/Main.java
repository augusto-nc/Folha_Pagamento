/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.desenvolvimentonc.folha_pagamento;

import java.util.Scanner;

/**
 *
 * @author Jose_Augusto
 */
public  class Main {

  






  
   
    public static class Undo{
        public int index;
        public int tipo;
        public Empregado empregado;
        public CartaoPonto cartaoPonto;
        public Venda venda;
        public taxaServico taxaServico;
        
    }
    
    public static class Empregado{
        public static final int SALARIO_HORISTA=1;
        public static final int SALARIO_MENSAL=2;
        public static final int SALARIO_COMISSAO=3;
        
        
        public static final int CHEQUE_MAOS=1;
        public static final int CHEQUE_CORREIO=2;
        public static final int DEPOSITO_CONTA=3;
        
        
        public boolean sindicato=false;
        double taxaSindical=0;
        public int idSindicato;
        public int tipoPagamento;
        public int agendaPagamento;
        public int tipo;
        public String nome;
        public String endereco;
        
     
    }
    
    public static class CartaoPonto{
        public int idEmpregado;
        public int HorasTrabalhadas;
        public int data;

    }
       public static class Venda{
        public int idEmpregado;
        public double valor;
        public int data;
    }
     public static class taxaServico{
        public int idEmpregado;
        public double valor;

    }
    
    
    public static Empregado[] empregados=new Empregado[100];
    public static Undo[] undos=new Undo[10];
    public static Undo[] redo=new Undo[10];

    public static CartaoPonto[] cartaoPontos=new CartaoPonto[1000];
    public static Venda[] vendas=new Venda[1000];
    public static taxaServico[] taxaServicos=new taxaServico[1000];
    public static String[] AGENDA_PAGAMENTO={"Mensalmente,Semanalmente,Bi-semanalmente",null,null,null,null,null,null,null};


    public static int indexEmpregado=0;
    public static int indexCartao=0;
    public static int indexVenda=0;
    public static int indexAgenda=3;
    public static int indexUndo=0;
    public static int sizeUndo=0;
    public static int indexRedo=0;
    public static int sizeRedo=0;


    
    private static int indexTaxaServicos=0;


    private static int data;

    static Scanner sc;
    public static void main(String[] args) {
        sc = new Scanner(System.in);
        int r=1;
        while(r!=0){
            
            System.out.println("1- Adicionar novo Empregado");
            System.out.println("2- Remover Empregado");
            System.out.println("3- Lançar Cartao de Ponto");
            System.out.println("4- Lançar Resultado de venda");
            System.out.println("5- Lançar Taxa de serviço");
            System.out.println("6- Editar Empregado");
            System.out.println("8- Undo/Redo");
            System.out.println("9- Editar Agenda Pagamento");
            System.out.println("10- Nova Agenda Pagamento");
            System.out.println("11- Listar Empregados");

            System.out.println("0- Sair");
            
            r=sc.nextInt();
            switch (r){
                case 1:
                    adicionarNovoEmpregado();
                    break;
                case 2:
                    removerEmpregado();
                    break;
                case 3:
                    lancarCartaoPonto();
                    break;
                 case 4:
                    lancarResultadoVenda();
                    break;
                 case 5:
                    taxaServico();
                    break;
                  case 6:
                    editarEmpregado();
                    break;
                  case 8:
                      undoRedo();
                   
                   case 9:
                    editarAgenda();
                    break;
                   case 10:
                      novaAgenda();
                      break;
                    case 11:
                      listarEmpregados();
                      break;

                    
          
            }
          
        }
        
    }
    
      private static void undoRedo() {
          System.out.println("1. Undo");
          System.out.println("2. Redo");
          int r= sc.nextInt();
          if(r==1){
              undo();
          }else if(r==2){
              redo();
          }
    }
    private static void novaAgenda() {
           System.out.print("Nome do agendamento: ");
           String nome=sc.next();
           AGENDA_PAGAMENTO[indexAgenda]=nome;
           indexAgenda++;
           System.out.println("Agendameno Criado com sucesso");
    }

    
    private static void adicionarNovoEmpregado() {
        undos[indexUndo]= new Undo();
        undos[indexUndo].empregado=null;
        undos[indexUndo].tipo=1; 
        undos[indexUndo].index=indexEmpregado;
        indexUndo=(indexUndo+1)%10;sizeUndo=sizeUndo+1>10 ? 10: sizeUndo+1;

        
         String nome;String endereco;int tipo=-987;
         System.out.print("Nome: ");
         nome=sc.next();
         System.out.print("Endereco: ");
         endereco=sc.next();
         
         while (tipo!=Empregado.SALARIO_COMISSAO &&  tipo!=Empregado.SALARIO_HORISTA && tipo!=Empregado.SALARIO_MENSAL) {             
            System.out.println("Escolha o Tipo: ");
            System.out.println("1-Salário Horista:");
            System.out.println("2-Salário Mensal:");
            System.out.println("3-Comissao:");
             if(tipo!=-987)
                  System.out.println("Tipo Inválido");
             tipo=sc.nextInt();
         }
         
         
         Empregado empregado=new Empregado();
         empregado.agendaPagamento= tipo==Empregado.SALARIO_MENSAL ? 0 :( tipo==Empregado.SALARIO_HORISTA ? 1 :0);
         empregado.nome=nome;
         empregado.tipo=tipo;
         empregado.endereco=endereco;
         empregados[indexEmpregado]= empregado;
         indexEmpregado++;
     }
     
    private static void removerEmpregado() {
    

        int r=1;
        while(r!=0){
             System.out.println("1- Listar Empregados");
             System.out.println("2- Remover empregdo pelo ID");
             System.out.println("0- Voltar");
              r=sc.nextInt();
              switch(r){
            case 1:
                listarEmpregados();
                break;
            case 2:
                System.out.print("id: ");
                r=sc.nextInt();
                if(r>=0 && r<100 && empregados[r]!=null){
                    undos[indexUndo]= new Undo();
                    undos[indexUndo].empregado=empregados[r];
                    undos[indexUndo].tipo=1; 
                    undos[indexUndo].index=indexEmpregado;
                    indexUndo=(indexUndo+1)%10;sizeUndo=sizeUndo+1>10 ? 10: sizeUndo+1;
                    
                    empregados[r]=null;
                    System.out.println("Removido com sucesso! ");
                }else{
                     System.out.println("Usuário nao encontrado ");
                }
         
        }  
        }
    
        
        
    }
    
    
    private static void lancarCartaoPonto() {
            undos[indexUndo]= new Undo();
            undos[indexUndo].tipo=2; 
            undos[indexUndo].index=indexCartao;
            indexUndo=(indexUndo+1)%10;sizeUndo=sizeUndo+1>10 ? 10: sizeUndo+1;
        
        
         int empregado;int horasTrabalhadas;
         empregado=-500;
         while(empregado<0 || empregado>=100 || empregados[empregado]==null || empregados[empregado].tipo!=Empregado.SALARIO_HORISTA ){
              if(empregado!=-500)
                     System.out.println("Empregado Inválido");
              System.out.print("Id empregado: ");
              empregado=sc.nextInt();
         }
         System.out.print("Horas Trabalhadas: ");
         horasTrabalhadas=sc.nextInt();
         CartaoPonto cartaoPonto=new CartaoPonto();
         cartaoPonto.idEmpregado=empregado;
         cartaoPonto.HorasTrabalhadas=horasTrabalhadas;
         cartaoPonto.data=data;
         cartaoPontos[indexCartao]= cartaoPonto;
         indexCartao++;
    }
    
    private static void lancarResultadoVenda() {
            undos[indexUndo]= new Undo();
            undos[indexUndo].tipo=3; 
            undos[indexUndo].index=indexVenda;
            indexUndo=(indexUndo+1)%10;sizeUndo=sizeUndo+1>10 ? 10: sizeUndo+1;
            
         int empregado;double valor;int data;
         empregado=-500;
         while(empregado<0 || empregado>=100 || empregados[empregado]==null || empregados[empregado].tipo!=Empregado.SALARIO_COMISSAO ){
              if(empregado!=-500)
                     System.out.println("Empregado Inválido");
              System.out.print("Id empregado: ");
              empregado=sc.nextInt();
         }
         System.out.print("Valor da venda: ");
         valor=sc.nextDouble();
         System.out.print("Data: ");
         data=sc.nextInt();
         Venda venda=new Venda();
         venda.idEmpregado=empregado;
         venda.valor=valor;
         venda.data=data;
         vendas[indexVenda]= venda;
         indexVenda++;
    }
       
    private static void taxaServico() {
        
          undos[indexUndo]= new Undo();
          undos[indexUndo].tipo=4; 
          undos[indexUndo].index=indexTaxaServicos;
          indexUndo=(indexUndo+1)%10;sizeUndo=sizeUndo+1>10 ? 10: sizeUndo+1;
        
         int empregado;double valor;int data;
         empregado=-500;
         while(empregado<0 || empregado>=100 || empregados[empregado]==null){
              if(empregado!=-500)
                     System.out.println("Empregado Inválido");
              System.out.print("Id empregado: ");
              empregado=sc.nextInt();
         }
         System.out.print("Valor da taxa de serviço: ");
         valor=sc.nextDouble();
    
         taxaServico taxaServico=new taxaServico();
         taxaServico.idEmpregado=empregado;
         taxaServico.valor=valor;
         taxaServicos[indexTaxaServicos]= taxaServico;
         indexTaxaServicos++;
    }
      
    
    private static void redo(){
        
        if(sizeRedo>0){
               indexRedo=(indexRedo+9)%10;         
               sizeRedo--;
               int index=redo[indexUndo].index;
               int tipo=redo[indexUndo].tipo;
               
                undos[indexRedo]= new Undo();
                undos[indexRedo].tipo=tipo;
                undos[indexRedo].index=index;
                indexUndo=(indexUndo+1)%10;sizeUndo=sizeUndo+1>10 ? 10: sizeUndo+1;
               
               
               if(tipo==1){
                    undos[indexUndo].empregado=empregados[index];
                    empregados[index]=redo[indexRedo].empregado;
               }else if(tipo==2){
                   undos[indexUndo].cartaoPonto=cartaoPontos[index];
                   cartaoPontos[index]=redo[indexRedo].cartaoPonto;
               }else if(tipo==3){
                    undos[indexUndo].venda=vendas[index];
                    vendas[index]=redo[indexRedo].venda;
               }else if(tipo==4){
                     undos[indexRedo].taxaServico=taxaServicos[index];
                     taxaServicos[index]=redo[indexRedo].taxaServico;
               }
             }
        
        
    }
    
    private static void undo(){
       
           if(sizeUndo>0){
               indexUndo=(indexUndo+9)%10;         
               sizeUndo--;
               int index=undos[indexUndo].index;
               int tipo=undos[indexUndo].tipo;
               
                redo[indexRedo]= new Undo();
                redo[indexRedo].tipo=tipo;
                redo[indexRedo].index=index;
                indexRedo=(indexRedo+1)%10;sizeRedo=sizeRedo+1>10 ? 10: sizeRedo+1;
               
               
               if(tipo==1){
                    redo[indexRedo].empregado=empregados[index];
                    empregados[index]=undos[indexUndo].empregado;
               }else if(tipo==2){
                   redo[indexRedo].cartaoPonto=cartaoPontos[index];
                   cartaoPontos[index]=null;
               }else if(tipo==3){
                    redo[indexRedo].venda=vendas[index];
                    vendas[index]=null;
               }else if(tipo==4){
                     redo[indexRedo].taxaServico=taxaServicos[index];
                     taxaServicos[index]=null;
               }
             }
           
           }     
                    
                    
        
    

    
    private static void copiarEmpregado(Empregado source,Empregado dest){
           dest.agendaPagamento=source.agendaPagamento;
          dest.nome=source.nome; dest.endereco=source.endereco;
          dest.tipo=source.tipo;   dest.tipoPagamento=source.tipoPagamento;
          dest.sindicato=source.sindicato;dest.idSindicato=source.idSindicato;
          dest.taxaSindical=source.taxaSindical;
        
    }
    
    private static void editarEmpregado(){
        int r=-1;
        Empregado empregado=null;
        while(r<0 || r>=100 || empregados[r]==null){
         if(r!=-1)
               System.out.println("Empregado não encontrado");
         System.out.print("id: ");
                r=sc.nextInt();   
        } 
          undos[indexUndo]= new Undo();
          undos[indexUndo].tipo=1; 
          undos[indexUndo].index=r;
          Empregado auxAnt=new Empregado();
          undos[indexUndo].empregado=auxAnt;
          indexUndo=(indexUndo+1)%10;sizeUndo=sizeUndo+1>10 ? 10: sizeUndo+1;
          
          empregado=empregados[r];
          copiarEmpregado(empregado, auxAnt);
     
        
        int k=-1;
        while(k!=0){
            System.out.println("1. Editar Nome"); 
            System.out.println("2. Editar Endereço"); 
            System.out.println("3. Editar Tipo de empregado"); 
            System.out.println("4. Editar Tipo de pagamento"); 
            System.out.println("5. Editar Sindicato"); 
            System.out.println("6. Editar Id Sindicato"); 
            System.out.println("7. Editar Taxa Sindical"); 
            System.out.println("0. Voltar");   
            k=sc.nextInt();
            switch (k){
                case 1:
                    System.out.print("Nome: ");
                    empregado.nome=sc.next();
                    break;
                case 2:
                    System.out.print("Endereço: ");
                    empregado.endereco=sc.next();
                    break;
                case 3:
                    System.out.println("Tipo de empregado: ");
                     System.out.println("1-Salário Horista:");
                     System.out.println("2-Salário Mensal:");
                     System.out.println("3-Comissao:");
                     int j=sc.nextInt();
                     if(j==Empregado.SALARIO_COMISSAO || j==Empregado.SALARIO_HORISTA || j==Empregado.SALARIO_MENSAL)
                           empregado.tipo=j;
                     else
                         System.out.println("Tipo inválido");
                     break;
                case 4:
                      System.out.println("Tipo de Pagamento: ");
                     System.out.println("1-Cheuqe em mãos:");
                     System.out.println("2-Cheque correios:");
                     System.out.println("3-Depósito em conta:");
                     j=sc.nextInt();
                     if(j==Empregado.CHEQUE_CORREIO || j==Empregado.CHEQUE_MAOS || j==Empregado.DEPOSITO_CONTA)
                           empregado.tipoPagamento=j;
                     else
                         System.out.println("Tipo inválido");
                     break;
                case 5:
                     System.out.print("Sindicato sim/nao");
                     empregados[r].sindicato=sc.next().equals("sim");
                      break;
                     
                case 6:
                        if(empregados[r].sindicato){
                        System.out.print("Tid Sindicato:");
                        empregados[r].idSindicato=sc.nextInt();
                    }else{
                        System.out.println("Não é sindicalizado");
                    }
                      break;
                    
                case 7:
                     if(empregados[r].sindicato){
                        System.out.print("Taxa Sindical:");
                        empregados[r].taxaSindical=sc.nextDouble();
                    }else{
                        System.out.println("Não é sindicalizado");
                    }
                      break;
                    
            
            }  
        }
        
        
        
        
    }    
     
    
    
    private static void editarAgenda() {
          int r=-1;
        Empregado empregado=null;
        while(r<0 || r>=100 || empregados[r]==null){
         if(r!=-1)
               System.out.println("Empregado não encontrado");
         System.out.print("id Empregado: ");
                r=sc.nextInt();   
        } 
        empregado=empregados[r];
        for(int i=0;i<indexAgenda;i++){
            System.out.println(i+"-"+AGENDA_PAGAMENTO[i]);
        }
        int j=sc.nextInt();
        if(j>=0 && j<indexAgenda){
              System.out.println("Agenda de Pagamento Alterada");
        }else{
            System.out.println("Agenda Inválida");
        }
        
        
        
        
    }
    private static void listarEmpregados() {
        for(int i=0;i<indexEmpregado;i++){
            if(empregados[i]!=null){
                printEmpregado(i);
                
           
            }
            
            
        }
        
    }

private static void printEmpregado(int i){
       System.out.println("id: "+i+", Nome: "+empregados[i].nome+", Tipo: "
                +(empregados[i].tipo==Empregado.SALARIO_MENSAL ? "Salario Mensal" : (empregados[i].tipo==Empregado.SALARIO_HORISTA ? "Salário Horista" : "Salário Comissionado"))
                +", Sindicato: "+(empregados[i].sindicato ?( "Sim, IdSindicato: "+empregados[i].idSindicato+", Taxa Sindical: "+empregados[i].taxaSindical) : "Não") +", endereço: "+empregados[i].endereco+
                " Pagamento: "+(empregados[i].tipoPagamento==Empregado.CHEQUE_CORREIO ? "Cheque Correios" :(empregados[i].tipoPagamento==Empregado.CHEQUE_MAOS ? "Cheque em Mãos" : "Depósito" ))
                );
}
    
    
}
