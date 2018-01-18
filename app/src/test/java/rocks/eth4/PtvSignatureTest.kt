package rocks.eth4

import org.junit.Assert.*
import org.junit.Test
import rocks.eth4.e4melbtram.utils.PtvKeyStore
import rocks.eth4.e4melbtram.utils.PtvSignatureUtil

/**
 * Created by eth4 on 19/1/18.
 */

class ExampleUnitTest {
    @Test
    fun signature_test() {
        assertEquals(PtvKeyStore.expectedUrl, PtvSignatureUtil.buildTTAPIURL("/v3/route_types") )
    }
}