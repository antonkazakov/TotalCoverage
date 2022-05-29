package otus.demo.totalcoverage.coreapi

import javax.inject.Provider

interface MediatorsMapProvider {

    fun provideMediatorsMap(): Map<Class<*>,  @JvmSuppressWildcards Provider<Any>>
}