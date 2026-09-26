package Poll;

public class PollTest {
    public static void main (String[] args) {
        PollDisplayPanel votingMachiine = new PollDisplayPanel("Tami", "Brian", "Liz");

        votingMachiine.vote1();
        votingMachiine.vote2();
        votingMachiine.vote3();

        System.out.println(votingMachiine);

    }
}
