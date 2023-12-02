import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class PoliticalAlignment {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] questions = { // 5 questions to ask the user
            "How should the government go about taxation?",
            "What should the government's role be in ensuring healthcare access for citizens?",
            "Should same-sex marriage be legally recognized and protected?",
            "What approach should the government take regarding immigration and border control?",
            "What is the best approach to gun control policy?"
        };

        

        String[][] options = { // Each option corresponds with specific party
            {
                "a.) Increase taxes of the wealthy.",
                "b.) Reduce taxes to stimulate economic growth and empower individuals to manage their own finances, promoting free market principles.",
                "c.) Taxation should be based on the individuals/corporations carbon emissions.",
                "d.) Less government involvement and minimal interference in both economic and personal matters."
            },
            {
                "a.) Ensure universal healthcare coverage for all citizens, possibly through a single-payer system, funded by taxes on higher income brackets.",
                "b.) Encourage a free-market approach to healthcare, allowing for competition among private insurers and reducing government intervention.",
                "c.) Advocate for a publicly funded, universal healthcare system that emphasizes preventive care and community health initiatives, funded through taxes, possibly including a tax on unhealthy products like sugary drinks or tobacco.",
                "d.) Promote a free-market healthcare system with minimal government involvement, allowing individuals to choose their coverage and providers freely, potentially advocating for health savings accounts and reducing regulations in the healthcare sector."
            },
            {
                "a.) It should be legally recognized as a fundamental human right, ensuring equal marriage rights and benefits for all individuals regardless of sexual orientation.",
                "b.) Marriage should be defined as between one man and one woman, preserving traditional values and religious beliefs.",
                "c.) Same-sex marriage should not only be legally recognized but actively promoted and celebrated as part of a broader commitment to social justice and inclusivity.",
                "d.) Government should not be involved in regulating marriage; it's a personal matter, and individuals should be free to define marriage as they see fit without government interference."
            },
            {
                "a.) Support comprehensive immigration reform, including a pathway to citizenship for undocumented immigrants, and prioritize humanitarian efforts for refugees and asylum seekers.",
                "b.) Emphasize strong border security measures, including building a physical barrier, and prioritize the enforcement of existing immigration laws to curb illegal immigration.",
                "c.) Advocate for open borders, viewing immigration as a human right, and promoting policies that prioritize welcoming immigrants and refugees, while addressing root causes of migration.",
                "d.) Support a streamlined immigration system that encourages legal immigration through simplified processes, reducing bureaucratic hurdles, and potentially implementing a guest worker program."
            },
            {
                "a.) Enforce universal background checks for all gun purchases, including at gun shows and online sales, and implement red flag laws to temporarily remove firearms from individuals deemed a threat to themselves or others.",
                "b.) Protect and uphold the Second Amendment rights of law-abiding citizens, focusing on enforcing existing laws rather than implementing new restrictions on firearms ownership.",
                "c.) Advocate for stringent gun control measures, including banning assault weapons, high-capacity magazines, and implementing strict licensing requirements for gun ownership.",
                "d.) Support the individual's right to bear arms without government interference, opposing most gun control measures and advocating for the removal of restrictions on firearm ownership, including concealed carry laws."
            }
        };

        System.out.print("Enter your first name: "); 
        String firstName = scanner.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        List<String> answers = new ArrayList<>();

        for (int i = 0; i < questions.length; i++) {
            System.out.println("\n" + questions[i]);
            for (String option : options[i]) {
                System.out.println(option);
            }
            System.out.println();
            String answer;
            do {
                System.out.print("Which do you agree with most (A, B, C, or D): ");
                answer = scanner.nextLine().toLowerCase();
            } while (!Arrays.asList("a", "b", "c", "d").contains(answer));
            answers.add(answer);
        }

        int democrat = 0, republican = 0, greenParty = 0, libertarian = 0;
        // Intialize each party to 0 so program has no prior knowledge of party


        for (int i = 0; i < answers.size(); i++) {
            double pointsToAdd = 1.0; // Default points for regular questions
            
            // Adjust points for same-sex marriage question (index 2)
            if (i == 2) {
                pointsToAdd = 2.5; // Adjust points for the specific question
            }
            
            String answer = answers.get(i);
            
            // Add the adjusted points to the respective parties
            if (answer.equals("a")) {
                democrat += pointsToAdd;
            } else if (answer.equals("b")) {
                republican += pointsToAdd;
            } else if (answer.equals("c")) {
                greenParty += pointsToAdd;
            } else if (answer.equals("d")) {
                libertarian += pointsToAdd;
            }
        }
        

        Map<String, Double> partyCounts = new HashMap<>();
        partyCounts.put("Democrat", (double) democrat);
        partyCounts.put("Republican", (double) republican);
        partyCounts.put("Green Party", (double) greenParty);
        partyCounts.put("Libertarian", (double) libertarian);

        String maxParty = Collections.max(partyCounts.entrySet(), Map.Entry.comparingByValue()).getKey();

        System.out.println("\nYou mostly align with " + maxParty); // Program's guess

        // Gets user's correct party

        String usersCorrectParty;
        boolean validInput;
        String partyOptions = "\nWhich is your correct party affiliation?\na.) Democrat\nb.) Republican\nc.) Green Party\nd.) Libertarian\n\nChoose A, B, C, or D: ";

        do {
            validInput = true;
            System.out.print(partyOptions);
            usersCorrectParty = scanner.nextLine().toLowerCase();
            
            if (!Arrays.asList("a", "b", "c", "d").contains(usersCorrectParty)) {
                validInput = false;
                System.out.print("Invalid answer, choose A, B, C, or D: ");
            }
        } while (!validInput);


        Map<String, String> partyMapping = Map.of(
            "a", "Democrat",
            "b", "Republican",
            "c", "Green Party",
            "d", "Libertarian"
        );

        String chosenParty = partyMapping.get(usersCorrectParty);
        // Stores user's answers in their correct party text file
        // Writing answers to text file
        if (chosenParty != null) {
            try {
                String filename = chosenParty + "_answers.txt";
                try (FileWriter fileWriter = new FileWriter(filename, true)) {
                    fileWriter.write(String.format("User: %s %s%n", firstName, lastName));
                    for (int i = 0; i < answers.size(); i++) {
                        fileWriter.write(String.format("Question %d: %s.)%n", i + 1, answers.get(i)));
                    }
                    fileWriter.close();
                } catch (IOException e) {
                    System.out.println("An error occurred while writing to file.");
                    e.printStackTrace();
                }
                System.out.printf("%s %s's answers saved to %s%n", firstName, lastName, filename);
            } catch (Exception e) {
                System.out.println("An error occurred while writing to file.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid party choice.");
        }

        scanner.close();
    }
}

// TO DO LIST
// 1 Fix when the user doesn pick abcd, its doesnt put new question
// 2  Fix when the user doesn pick abcd for correct party, it restates the whole wuestion again
// 3 Fix scoring system