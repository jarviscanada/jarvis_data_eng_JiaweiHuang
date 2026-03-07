package ca.jrvs.apps.practice;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface LambdaStreamExc {
    /**
     * create a String stream from array
     * @param strings
     * @return
     */

    Stream<String> createStrStream(String ... strings);

    /**
     * convert all strings to upper case
     * @param stream
     * @return
     */
    Stream<String> ToUpperCase(Stream<String> stream);

    /**
     * filter strings that contains the pattern
     * @param stringStream
     * @param pattern
     * @return
     */
    Stream<String> filter(Stream<String> stringStream, String pattern);

    /**
     * create a IntStream from a arr[]
     * @param arr
     * @return
     */
    IntStream createIntStream(int[] arr);

    /**
     * convert a Intstream to list
     * @param stream
     * @return
     * @param <E>
     */
    <E> List<E> toList(Stream<E> stream);

    /**
     * create a IntStream range from start to end inclusive
     * @param start
     * @param end
     * @return
     */
    IntStream createIntStream(int start, int end);

    /**
     * convert a intStream to a doubleStream
     * @param intStream
     * @return
     */
    DoubleStream squareRootIntStream(IntStream intStream);


    /**
     * return odd numbers from a IntStream
     * @param intStream
     * @return
     */
    IntStream getOdd(IntStream intStream);

    /**
     * Return a lambda function that print a message with a prefix and suffix
     * This lambda can be useful to format logs
     * @param prefix
     * @param suffix
     * @return
     */
    Consumer<String> getLambdaPrinter(String prefix, String suffix);

    /**
     * Print each message with a given printer
     * @param messages
     * @param printer
     */
    void printMessages(String[] messages, Consumer<String> printer);

    /**
     * Print all odd number from a intStream
     * @param intStream
     * @param printer
     */
    void printOdd(IntStream intStream, Consumer<String> printer);

    /**
     * Square each number from the input
     * @param ints
     * @return
     */
    Stream<Integer> flatNestedInt(Stream<List<Integer>> ints);

}
