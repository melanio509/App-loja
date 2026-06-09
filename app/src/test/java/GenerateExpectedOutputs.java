import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Classe privada do formador.
 *
 * Gera os ficheiros .out oficiais através da solução de referência.
 * Não deve ser entregue aos alunos.
 */
public class GenerateExpectedOutputs {

    private static final Duration TIMEOUT =
            Duration.ofSeconds(5);

    private static final String APP_MAIN_CLASS =
            "atec.poo.loja.app.App";

    private static final Path TEST_DIR =
            Path.of("src", "test", "java");

    private static final Path INPUT_DIR =
            TEST_DIR.resolve("in");

    private static final Path EXPECTED_OUTPUT_DIR =
            TEST_DIR.resolve("out");

    public static void main(String[] args) throws Exception {
        Files.createDirectories(EXPECTED_OUTPUT_DIR);

        List<Path> inputFiles = findInputFiles();

        if (inputFiles.isEmpty()) {
            System.out.println(
                    "Não foram encontrados ficheiros .in em: "
                            + INPUT_DIR.toAbsolutePath()
            );

            return;
        }

        System.out.println("==========================================");
        System.out.println(" GERAÇÃO DOS OUTPUTS OFICIAIS");
        System.out.println("==========================================");

        for (Path inputFile : inputFiles) {
            generateOutput(inputFile);
        }

        System.out.println();
        System.out.println("Outputs gerados com sucesso.");
        System.out.println("Revê os ficheiros antes de os distribuir.");
    }

    private static void generateOutput(Path inputFile)
            throws Exception {

        String testName = removeExtension(
                inputFile.getFileName().toString()
        );

        Path expectedOutput =
                EXPECTED_OUTPUT_DIR.resolve(testName + ".out");

        Path importFile =
                INPUT_DIR.resolve(testName + ".import");

        Files.deleteIfExists(expectedOutput);

        List<String> command = new ArrayList<>();

        command.add(javaExecutable());
        command.add("-Din=" + inputFile.toAbsolutePath());
        command.add("-Dout=" + expectedOutput.toAbsolutePath());

        if (Files.exists(importFile)) {
            command.add(
                    "-Dimport=" + importFile.toAbsolutePath()
            );
        }

        command.add("-cp");
        command.add(absoluteClasspath());
        command.add(APP_MAIN_CLASS);

        ProcessBuilder processBuilder =
                new ProcessBuilder(command);

        processBuilder.inheritIO();

        Process process = processBuilder.start();

        boolean finished = process.waitFor(
                TIMEOUT.toMillis(),
                TimeUnit.MILLISECONDS
        );

        if (!finished) {
            process.destroyForcibly();
            process.waitFor();

            throw new IllegalStateException(
                    "O teste " + testName
                            + " excedeu o tempo limite."
            );
        }

        if (process.exitValue() != 0) {
            throw new IllegalStateException(
                    "A aplicação terminou com erro ao gerar "
                            + expectedOutput
            );
        }

        System.out.println(
                "[GERADO] " + expectedOutput
        );
    }

    private static List<Path> findInputFiles()
            throws IOException {

        try (Stream<Path> files = Files.list(INPUT_DIR)) {
            return files
                    .filter(Files::isRegularFile)
                    .filter(path -> path
                            .getFileName()
                            .toString()
                            .endsWith(".in"))
                    .sorted(
                            Comparator.comparing(
                                    path -> path
                                            .getFileName()
                                            .toString()
                            )
                    )
                    .toList();
        }
    }

    private static String javaExecutable() {
        boolean windows =
                System.getProperty("os.name")
                        .toLowerCase()
                        .contains("win");

        String filename =
                windows ? "java.exe" : "java";

        return Path.of(
                System.getProperty("java.home"),
                "bin",
                filename
        ).toString();
    }

    private static String absoluteClasspath() {
        String separator =
                System.getProperty("path.separator");

        return Arrays.stream(
                        System.getProperty("java.class.path")
                                .split(
                                        Pattern.quote(separator)
                                )
                )
                .map(path -> Path.of(path)
                        .toAbsolutePath()
                        .normalize()
                        .toString())
                .collect(
                        Collectors.joining(separator)
                );
    }

    private static String removeExtension(
            String filename
    ) {
        int index = filename.lastIndexOf('.');

        return index < 0
                ? filename
                : filename.substring(0, index);
    }
}