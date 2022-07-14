import java.io.*;
import java.util.*;

public class Main {
    public static void clear() throws IOException, InterruptedException {
        if(System.getProperty("os.name").contains("Windows"))
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
            new ProcessBuilder("clear").inheritIO().start().waitFor();
    }

    static Scanner input = new Scanner(System.in);

    static HashMap<String, User> users = new HashMap<String, User>();
    static HashMap<String, Project> projects = new HashMap<String, Project>();

    public static void title() {
        System.out.println("---------------------------------------------------");
        System.out.println("       SISTEMA GERENCIADOR DE PROJETOS - SGP       ");
        System.out.println("---------------------------------------------------");
    }

    public static void editProject(Project project) throws IOException, InterruptedException {
        clear();
        int option = -1;
        do {
            title();
            System.out.println("|1| Alterar status");
            System.out.println("|2| Editar descrição");
            System.out.println("|3| Adicionar participante");
            System.out.println("|0| Voltar");
            System.out.println();
            System.out.print("> ");
            
            try {
                option = Integer.parseInt(input.next());
                switch(option) {
                    case 1:
                        clear();
                        if(project.getStatus().equals("Em processo de criação")) {
                            if(project.getDescription().isEmpty() || project.getParticipants().isEmpty())
                                System.out.println("INFORMAÇÕES IMCOMPLETAS NO PROJETO");
                            else 
                                project.setStatus("Iniciado");
                        }
                        break;
                    case 2:
                        System.out.println();    
                        System.out.println("|1| Nova descrição");
                        System.out.println("|2| Remover descrição");
                        System.out.println();
                        System.out.print("> ");

                        int option2 = Integer.parseInt(input.next());
                        if(option2 == 1) {
                            System.out.print("Nova descrição: ");
                            String description = input.nextLine();
                            project.setDescription(description);
                        }
                        else if(option2 == 2) {
                            project.setDescription(null);
                        }
                        clear();
                        break;
                    case 3:
                        System.out.println();
                        System.out.print("Informe email do novo participante: ");
                        String email = input.next();
                        if(!email.matches("^([\\w\\-]+.)*[\\w\\-]+@([\\w\\-]+.)+([\\w\\-]{2,3})")) throw new Exception("EMAIL INVALIDO");
                        if(!users.containsKey(email)) throw new Exception("USUÁRIO NÃO ENCONTRADO");

                        project.setPaticipant(users.get(email));
                        users.get(email).setProject(project);
                        clear();
                        break;
                    default:
                        clear();
                }
            } catch(NumberFormatException e) {
                clear();
                System.out.println("OPÇÃO INVÁLIDA");
            } catch(Exception e) {
                clear();
                System.out.println(e.getMessage());
            }
        } while(option != 0);
    }

    public static void selectProject(Boolean author, Project project) throws IOException, InterruptedException {
        int option = -1;
        do {
            clear();
            title();
            System.out.println("Autor: "+project.getAuthor().getFullName());
            System.out.println("Status: "+project.getStatus());
            System.out.println("Nome: "+project.getIdentification());
            System.out.println("Descrição: "+project.getDescription());
            System.out.println("Data de início: "+project.getBegin()+" | "+project.getTimeBegin());
            System.out.println("Data de termino: "+project.getEnd()+" | "+project.getTimeEnd());
            System.out.println();
            System.out.print("Participantes: ");
            for(User u : project.getParticipants()) {
                if(!u.equals(project.getParticipants().get(0))) System.out.print(", ");
                System.out.print(u.getFullName());
            }
            System.out.println();
            System.out.println();
            System.out.print("Atividades: ");
            for(Project p : project.getActivities()) {
                if(!p.equals(project.getActivities().get(0))) System.out.print(", ");
                System.out.print(p.getIdentification());
            }
            try {
                System.out.println();
                System.out.println();
                if(author == true) System.out.println("|1| Editar projeto");
                System.out.println("|0| Voltar");
                System.out.println();
                System.out.print("> ");
                input.nextLine();
                option = Integer.parseInt(input.next());
                if(option == 1) if(author == true) editProject(project);
            } catch(NumberFormatException e) {
                System.out.println("OPÇÃO INVÁLIDA");
            }
        } while(option != 0);
        clear();
    }

    public static void listProjectsUser(User user) throws IOException, InterruptedException {
        title();
        if(!user.getProjects().isEmpty()) {
            int i = 1;    
            for (Project p : user.getProjects())
                System.out.println("|"+(i++)+"| "+p.getIdentification());
        }
        else System.out.println("Vazio");

        try {
            System.out.println();
            System.out.println("|0| Voltar");
            System.out.println();
            System.out.print("> ");
            int index = Integer.parseInt(input.next());
            selectProject(((user.getProjects().get(index-1).getAuthor().equals(user)) ? true : false), user.getProjects().get(index-1));
        } catch(Exception e) {
            System.out.println("OPÇÃO INVÁLIDA");
        }
        clear();
    }

    public static void search() throws IOException, InterruptedException {
        input.nextLine();
        int option = -1;
        do {
            title();
            System.out.println("|1| Consultar por usuário");
            System.out.println("|2| Consultar por projeto");
            System.out.println("|0| Voltar");
            System.out.println();
            System.out.print("> ");

            try {
                option = Integer.parseInt(input.next());

                switch(option) {
                    case 1:
                        System.out.println();    
                        System.out.print("Informe email do usuário: ");
                        String email = input.next();

                        clear();
                        if(!email.matches("^([\\w\\-]+.)*[\\w\\-]+@([\\w\\-]+.)+([\\w\\-]{2,3})")) throw new Exception("EMAIL INVALIDO");
                        if (!users.containsKey(email)) throw new Exception("USUÁRIO NÃO ENCONTRADO");

                        listProjectsUser(users.get(email));
                        break;
                    case 2:
                        System.out.println();    
                        System.out.print("Informe nome do projeto: ");
                        String name = input.next();
                        if(!projects.containsKey(name)) throw new Exception("PROJETO NÃO ENCONTRADO");

                        selectProject(false, projects.get(name));
                        break;
                }
                clear();
            } catch(NumberFormatException e) {
                clear();
                System.out.println("OPÇÃO INVÁLIDA");
            } catch(Exception e) {
                clear();
                System.out.println(e.getMessage());
            }
        } while(option != 0);
    }

    public static Project create(boolean project, boolean activity) throws IOException, InterruptedException {
        input.nextLine();
        try {
            System.out.print("Nome d"+((project) ? "o projeto" : "a atividade")+": ");
            String name = input.nextLine();
            if(project) if(projects.containsKey(name)) throw new Exception("PROJETO JÁ EXISTE");

            System.out.print("Descrição: ");
            String description = input.nextLine();
            System.out.print("Data de início: ");
            String begin = input.next();
            System.out.print("Hora de início: ");
            String timeBegin = input.next();
            System.out.print("Data de término: ");
            String end = input.next();
            System.out.print("Hora de término: ");
            String timeEnd = input.next();

            Project p = new Project("Em processo de criação", name, description, begin, timeBegin, end, timeEnd);
            if(activity) {
                System.out.print("Responável pela atividade(informe o email): ");
                String email = input.next();
                clear();
                if(!email.matches("^([\\w\\-]+.)*[\\w\\-]+@([\\w\\-]+.)+([\\w\\-]{2,3})")) throw new Exception("EMAIL INVALIDO");
            }
            else {
                clear();
                projects.put(p.getIdentification(), p);

                return p;
            }
        } catch(Exception e) {
            clear();
            System.out.println(e.getMessage());
        }

        return null;
    }

    public static void homePage(User connected) throws IOException, InterruptedException {
        clear();
        int option = -1;
        do {
            input.nextLine();
            title();
            System.out.println("|1| Criar projeto");
            System.out.println("|2| Meus projetos");
            System.out.println("|3| Buscar projeto");
            System.out.println("|0| Sair");
            System.out.println();
            System.out.print("> ");

            try {
                option = Integer.parseInt(input.next());

                clear();
                title();
                switch(option) {
                    case 1:
                        if(connected.getOffice().equals("Professor") || connected.getOffice().equals("Pesquisador")) {
                            Project project = create(true, false);
                            if(project != null) {
                                project.setAuthor(connected);
                                connected.setProject(project);
                            }
                        }
                        else {
                            clear();
                            System.out.println("APENAS PROFESSORES OU PESQUISADORES PODEM CRIAR PROJETOS");
                        }
                        break;
                    case 2:
                        clear();
                        listProjectsUser(connected);
                        break;
                    case 3:
                        clear();
                        search();
                        break;
                    default:
                        clear();
                }
            } catch(NumberFormatException e) {
                System.out.println("OPÇÃO INVÁLIDA");
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        } while(option != 0);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        clear();
        int option = -1;
        do {
            title();
            System.out.println("|1| Login");
            System.out.println("|2| Criar conta");
            System.out.println("|3| Consultar projetos");
            System.out.println("|0| Sair");
            System.out.println();
            System.out.print("> ");
            
            try {
                option = Integer.parseInt(input.next());

                clear();
                title();
                switch(option) {
                    case 1:
                        System.out.print("Email: ");
                        String email = input.next();
                        if(!email.matches("^([\\w\\-]+.)*[\\w\\-]+@([\\w\\-]+.)+([\\w\\-]{2,3})")) throw new Exception("EMAIL INVALIDO");

                        System.out.print("Senha: ");
                        String password = input.next();

                        if(!users.containsKey(email)) throw new Exception("USUÁRIO NÃO ENCONTRADO");
                        if(!users.get(email).getPassword().equals(password)) throw new Exception("USUÁRIO NÃO ENCONTRADO");
                        
                        homePage(users.get(email));
                        break;
                    case 2:
                        System.out.print("Nome: ");
                        String name = input.next();
                        if(!name.matches("[A-z]+")) throw new Exception("NOME NÃO PODE TER ACENTOS OU NÚMEROS");
                        name = name.substring(0,1).toUpperCase().concat(name.substring(1));

                        System.out.print("Sobrenome: ");
                        String lastName = input.next();
                        if(!lastName.matches("[A-z]+")) throw new Exception("SOBRENOME NÃO PODE TER ACENTOS OU NÚMEROS");
                        lastName = lastName.substring(0,1).toUpperCase().concat(lastName.substring(1));

                        String office = "";
                        System.out.println();
                        System.out.println("Cargo:");
                        System.out.println();
                        System.out.println("|1| Aluno");
                        System.out.println("|2| Professor");
                        System.out.println("|3| Pesquisador");
                        System.out.println("|4| Profissional");
                        System.out.println("|5| Técnico");
                        System.out.println();
                        System.out.print("> ");
                        int option2 = Integer.parseInt(input.next());
                        if(option2 == 1) office = "Aluno";
                        if(option2 == 2) office = "Professor";
                        if(option2 == 3) office = "Pesquisador";
                        if(option2 == 4) office = "Profissional";
                        if(option2 == 5) office = "Técnico";

                        System.out.println();
                        System.out.print("Email: ");
                        email = input.next();
                        if(!email.matches("^([\\w\\-]+.)*[\\w\\-]+@([\\w\\-]+.)+([\\w\\-]{2,3})")) throw new Exception("EMAIL INVALIDO");
                        if(users.containsKey(email))  throw new Exception("EMAIlL JÁ CADASTRADO");

                        System.out.print("Senha: ");
                        password = input.next();

                        User user = new User(name, lastName, email, office, password);
                        users.put(email, user);
                        clear();
                        break;
                    case 3:
                        clear();
                        search();
                        break;
                    default:
                        clear();
                }
            } catch(NumberFormatException e) {
                clear();
                System.out.println("OPÇÃO INVÁLIDA");
            } catch(Exception e) {
                clear();
                System.out.println(e.getMessage());
            }
        } while(option != 0);
    }
}