package com.tdd.parallel.core;

import lombok.NoArgsConstructor;
import org.junit.Ignore;
import org.springframework.test.annotation.DirtiesContext;
import reactor.blockhound.BlockHound;

@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
@Ignore
@NoArgsConstructor
public class BlockhoundCalls {

    static void liberarMetodos() {

        //EXCECOES DE METODOS BLOQUEANTES DETECTADOS PELO BLOCKHOUND:
        BlockHound
                .install(builder -> builder
                                 .allowBlockingCallsInside("java.io.PrintStream",
                                                           "write"
                                                          )
                                 .allowBlockingCallsInside("java.io.FileOutputStream",
                                                           "writeBytes"
                                                          )
                                 .allowBlockingCallsInside("java.io.PrintStream",
                                                           "write"
                                                          )
                                 .allowBlockingCallsInside("java.io.FileOutputStream",
                                                           "writeBytes"
                                                          )
                                 .allowBlockingCallsInside("java.io.BufferedOutputStream",
                                                           "flushBuffer"
                                                          )
                                 .allowBlockingCallsInside("java.io.BufferedOutputStream",
                                                           "flush"
                                                          )
                                 .allowBlockingCallsInside("java.io.OutputStreamWriter",
                                                           "flushBuffer"
                                                          )
                                 .allowBlockingCallsInside("java.io.PrintStream",
                                                           "print"
                                                          )
                                 .allowBlockingCallsInside("java.io.PrintStream",
                                                           "println"
                                                          )
                        );
    }
}
